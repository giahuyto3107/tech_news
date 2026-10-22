package com.example.tech_news

import com.example.tech_news.core.data.repository.ItemRepository
import com.example.tech_news.core.data.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.DefaultAsserter.assertNotNull
import kotlin.test.DefaultAsserter.assertTrue

class NetworkTesting {
    private val itemRepository = ItemRepository()
    private val userRepository = UserRepository()

    @Test
    fun testHackerNewsApi() = runBlocking {
        // 1. Fetch Top Story IDs
        val topStoryIds = itemRepository.getTopStoryIds()
        println("Fetched ${topStoryIds.size} top stories: ${topStoryIds.take(5)}")
        assertTrue("Top story IDs list should not be empty", topStoryIds.isNotEmpty())

        // 2. Fetch Best Story IDs
        val bestStoryIds = itemRepository.getBestStoryIds()
        println("Fetched ${bestStoryIds.size} best stories: ${bestStoryIds.take(5)}")
        assertTrue("Best story IDs list should not be empty", bestStoryIds.isNotEmpty())

        // 3. Fetch New Story IDs
        val newStoryIds = itemRepository.getNewStoryIds()
        println("Fetched ${newStoryIds.size} new stories: ${newStoryIds.take(5)}")
        assertTrue("New story IDs list should not be empty", newStoryIds.isNotEmpty())

        // 4. Fetch the first Story Item
        val firstId = topStoryIds.first()
        val item = itemRepository.getItem(firstId)
        println("Fetched Story: Title = '${item?.title}', Author = '${item?.by}', Score = ${item?.score}")
        assertNotNull("Item should not be null", item)

        // 5. Fetch the max Story Item
        val maxId = itemRepository.getMaxItem()
        val maxItem = itemRepository.getItem(maxId)
        println("Fetched max item: Title = '${maxItem?.title}', Author = '${maxItem?.by}', Score = ${maxItem?.score}")
        assertNotNull("Item should not be null", item)

        // 6. Fetch User details for the author of that story
        val authorUsername = item?.by
        if (authorUsername != null) {
            val user = userRepository.getUser(authorUsername)
            println("Fetched User: Username = ${user?.id}, Karma = ${user?.karma}")
            assertNotNull("User should not be null", user)
        }
    }
}