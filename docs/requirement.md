# Offline-First Tech News Reader: Requirements

**Platform:** Native Android app (Kotlin, Jetpack Compose) · **Data Source:** Hacker News API · **Scope:** Solo project, v1

---

## 1. Purpose and Goals

Build a tech news reader that works fully offline and demonstrates the architecture skills most requested in junior-to-mid Android roles: offline-first data flow, clean layering, modern Compose UI, background work, and tests.

### Success Criteria
- Reads, bookmarks, and search work with no connection once content has been cached.
- Architecture is clear enough to explain in an interview (single source of truth, unidirectional data flow).
- Published to Google Play with a `README` that documents trade-offs.

### Non-Goals (v1)
- No custom backend, user accounts, or cloud sync.
- No posting, voting, or commenting on Hacker News (the API is read-only).
- No iOS or tablet-specific layouts.

---

## 2. Users and Scenarios

- **Commuter:** Opens the app on a train with no signal and reads articles prefetched earlier.
- **Casual reader:** Scrolls the top stories, bookmarks a few, and reads them later.
- **Searcher:** Looks up a past story or topic among saved content.

---

## 3. Data Source

Hacker News official API (Firebase-hosted, read-only, no key). Key endpoints: story ID lists (`top`, `new`, `best`, `ask`, `show`, `jobs`) and single-item lookup, where each story or comment is fetched by ID.

### Implications for the Design
- Listing a feed means fetching a list of IDs, then fetching each item. Requests must be batched and parallelized with limits.
- Comment threads are trees. Building one requires recursive fetching, so depth and count must be capped.
- The API has no search. v1 searches only locally cached content. Optionally add the Algolia HN Search API as a later enhancement.
- Keep requests polite even though no rate limit is documented: cache aggressively and avoid refetching unchanged items.

---

## 4. Functional Requirements

### 4.1 Feeds
- **FR-1:** Show feeds for Top, New, Best, Ask, and Show, switchable via tabs.
- **FR-2:** Each story card shows title, domain, score, comment count, author, and relative time.
- **FR-3:** Feeds paginate (infinite scroll) and load from the local database first, then refresh from the network.
- **FR-4:** Pull-to-refresh updates the current feed.
- **FR-5:** Show a "last updated" indicator and a clear offline banner when there is no connection.

### 4.2 Reading
- **FR-6:** Story detail screen shows metadata and an action to open the article.
- **FR-7:** Open the article in an in-app browser (Custom Tabs) by default; allow an external browser as an option.
- **FR-8:** Comments screen shows a threaded, collapsible comment tree, with HTML text rendered safely.
- **FR-9:** Stories are marked read once opened. Read items are visually distinct.

### 4.3 Offline and Bookmarks
- **FR-10:** Bookmark or unbookmark any story from the feed or detail screen.
- **FR-11:** Bookmarked stories and their comments are saved for offline reading and are never evicted by cache cleanup.
- **FR-12:** A Saved tab lists bookmarks with sort (recently saved, score) and swipe-to-remove with undo.
- **FR-13:** Background prefetch downloads the top $N$ stories (plus top-level comments and one reply level, up to 50 comments per story; bookmarked stories get up to 4 levels and 200 comments) on a schedule, by default on Wi-Fi only and while charging or on non-low battery.
- **FR-14:** *Deferred to post-v1:* Save the article's readable text for bookmarked stories, since external pages cannot be cached reliably otherwise.

### 4.4 Search
- **FR-15:** Search cached stories by title and domain with debounced input and instant results.
- **FR-16:** Use full-text search (Room FTS) for ranked matching. Keep recent search history, with an option to clear it.

### 4.5 Settings
- **FR-17:** **Theme:** System, light, dark, plus dynamic color on supported devices.
- **FR-18:** **Prefetch controls:** On/off, Wi-Fi only, frequency, and number of stories.
- **FR-19:** **Storage controls:** Show cache size, clear cache (excluding bookmarks), and cache retention period (default 30 days).
- **FR-20:** **Default link behavior:** In-app or external browser.

### 4.6 Sharing and Deep Links
- **FR-21:** Share a story link or title via the system share sheet.
- **FR-22:** *(Stretch)* Open `news.ycombinator.com/item?id=...` links directly in the app's detail screen.

---

## 5. Non-Functional Requirements

| Area | Requirement |
| :--- | :--- |
| **Offline** | Every screen renders from the local database. A network failure never produces a blank screen if cached data exists. |
| **Performance** | Cold start under 2 seconds on a mid-range device. Feed scrolling stays smooth with no visible jank (verified with recomposition checks and baseline profiles). |
| **Reliability** | Network errors, timeouts, and malformed items are handled and surfaced as non-blocking UI states, with retry. |
| **Battery and Data** | Background work respects battery and network constraints and can be disabled. |
| **Storage** | Cache is bounded with an eviction policy. Bookmarks are exempt. |
| **Accessibility** | Content descriptions, `48dp` touch targets, scalable text, and sufficient contrast. |
| **Privacy** | No analytics or accounts in v1. No personal data leaves the device. |
| **Compatibility** | Android 8.0 (API 26) and above. Portrait and landscape both supported. |
| **Localization** | English UI. All strings externalized so more languages can be added. |

---

## 6. Architecture Requirements

- **Pattern:** MVVM with unidirectional data flow. Immutable UI state exposed from ViewModels as `StateFlow`, and one-off events handled separately.
- **Layers:** UI (Compose) $\rightarrow$ ViewModel $\rightarrow$ Repository $\rightarrow$ Local (Room) and Remote (Retrofit) data sources. A domain layer with use cases is added only where logic is non-trivial.
- **Single source of truth:** Room. The UI observes the database only. The network updates the database.
- **Pagination:** Paging 3 with `RemoteMediator` backed by Room.
- **Dependency injection:** Koin.
- **Concurrency:** Coroutines and `Flow`, with injected dispatchers for testability.
- **Background work:** WorkManager for periodic prefetch with constraints.
- **Preferences:** DataStore.
- **Navigation:** Navigation Compose with type-safe routes.
- **Modularization *(Stretch)*:** Split into `app`, `core` (database, network, design system), and `feature` modules once v1 is stable.

---

## 7. Data Model (Draft)

- **`Story`:** `id`, `title`, `url`, `domain`, `author`, `score`, `commentCount`, `time`, `type`, `feedMembership`, `rank`, `fetchedAt`
- **`Comment`:** `id`, `storyId`, `parentId`, `author`, `text`, `time`, `depth`, `isDeleted`, `isDead`
- **`Bookmark`:** `storyId`, `savedAt`
- **`ReadState`:** `storyId`, `readAt`
- **`SearchHistory`:** `query`, `lastUsed`
- **`RemoteKeys` / `FeedState`:** Supports Paging 3 remote mediation.
- **FTS Table:** Room FTS table over story `title` and `domain`.

---

## 8. Error and Edge Cases

- Deleted, dead, or `null` items returned by the API are skipped without crashing.
- Stories with no URL (Ask HN, text posts) show their text body and have no "open article" action.
- Very large comment threads are truncated with a "load more" affordance.
- Partial refresh failures keep existing cached data and show a non-blocking error.
- Offline with an empty cache shows a clear empty state with a retry action.
- Link-opening failures (no browser available) show a message and offer to copy the link.

---

## 9. Testing Requirements

- **Unit tests:** Repositories, mappers, use cases, and ViewModels (JUnit, MockK, Turbine, coroutine test utilities).
- **Database tests:** DAOs and FTS queries using an in-memory Room database.
- **Remote mediator tests:** Refresh, append, and error paths against a fake API.
- **UI tests:** Key Compose screens (feed, detail, saved, search) with fake data.
- **Worker tests:** Prefetch worker behavior with WorkManager testing utilities.
- **Target:** Meaningful coverage of the data and ViewModel layers, rather than a raw percentage.

---

## 10. Tooling and Delivery

- **GitHub repository** with a clear `README`: screenshots, architecture diagram, and a short "Decisions and Trade-offs" section.
- **GitHub Actions CI:** Build, lint, and unit tests on every pull request.
- **Code quality:** Static analysis (`ktlint` or `detekt`) and Gradle version catalog (`libs.versions.toml`).
- **Release:** Build with R8 and signed App Bundle, published to Google Play (internal testing first).

---

## 11. Milestones

1. **Foundation:** Project setup, DI, navigation shell, design system basics, CI.
2. **Data layer:** Retrofit client, Room schema, repository, Paging with `RemoteMediator` for Top stories.
3. **Core UI:** Feed tabs, story card, detail screen, read state, offline banner.
4. **Comments:** Recursive fetching with limits, threaded collapsible UI.
5. **Offline features:** Bookmarks, Saved tab, background prefetch, cache eviction.
6. **Search and settings:** FTS search, history, settings screen with DataStore.
7. **Quality pass:** Tests, accessibility review, performance profiling, baseline profile.
8. **Release:** `README`, store listing, internal test, production release.

---

## 12. Future Enhancements (Post-v1)

- Algolia-based online search and historical stories.
- Firebase Auth with cloud sync of bookmarks and read state.
- Home-screen widget (Jetpack Glance) showing top stories.
- Notifications for keyword or score-threshold alerts.
- Offline readable-text extraction for saved articles.
- Multi-module architecture, tablet layouts, and localization.

---

## 13. Decisions Log

- **Dependency injection:** Koin.
- **Comment prefetch:** Top-level comments plus one reply level, up to 50 per story; bookmarked stories get up to 4 levels and 200 comments.
- **Offline article text (FR-14):** Deferred to post-v1.
- **Cache retention:** 30 days; bookmarks are exempt.