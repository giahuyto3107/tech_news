package com.example.tech_news.features.feeds.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.tech_news.R
import com.example.tech_news.core.app_enum.Categories
import com.example.tech_news.core.app_enum.StringNote
import com.example.tech_news.core.components.NewsItem
import com.example.tech_news.core.constants.Type
import com.example.tech_news.core.item.data.model.Item
import com.example.tech_news.core.theme.LightErrorContainer
import com.example.tech_news.core.theme.LightOnErrorContainer
import com.example.tech_news.core.theme.LocalTechNewsDimens
import com.example.tech_news.core.theme.TechNewsShapes
import com.example.tech_news.core.theme.TechNewsTypography

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = modifier) {
        HeadingSection(
            modifier = Modifier.fillMaxWidth()
        )
        StatusBar(
            statusTitle = stringResource(R.string.offline_title),
            statusDescription = stringResource(R.string.offline_message),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LocalTechNewsDimens.current.spaceMedium)
        )
        CategoryTabs(
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(LocalTechNewsDimens.current.space2XSmall))
        ContentSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LocalTechNewsDimens.current.spaceMedium)
        )
        AllCatchUpText(
            title = stringResource(R.string.all_caught_up),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HeadingSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.feeds_eyebrow),
            style = TechNewsTypography.labelMedium,
            fontWeight = FontWeight.Medium
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Column {
                Text(
                    stringResource(R.string.feeds_title),
                    style = TechNewsTypography.headlineMedium
                )

                Text(
                    StringNote.HardcodedString.toString(),
                    style = TechNewsTypography.labelSmall,
                    fontWeight = FontWeight.Light
                )
            }

            Icon(
                imageVector = Icons.Default.Loop,
                contentDescription = stringResource(R.string.refresh_button),
                tint = Color.Gray
            )
        }

    }
}

@Composable
fun StatusBar(
    statusTitle: String,
    statusDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(TechNewsShapes.small)
            .background(LightErrorContainer)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = LocalTechNewsDimens.current.spaceMedium,
                vertical = LocalTechNewsDimens.current.spaceMedium,
            )
        ) {
            Icon(
                Icons.Default.WifiOff,
                contentDescription = statusTitle,
                tint = LightOnErrorContainer,
            )
            Spacer(Modifier.width(LocalTechNewsDimens.current.spaceSmall))
            Column {
                Text(
                    statusTitle,
                    color = LightOnErrorContainer,
                    fontWeight = FontWeight.SemiBold,
                    style = TechNewsTypography.bodyMedium
                )
                Spacer(Modifier.height(LocalTechNewsDimens.current.spaceExtraSmall))
                Text(
                    statusDescription,
                    fontWeight = FontWeight.Light,
                    style = TechNewsTypography.labelSmall
                )
            }
        }
    }
}

@Composable
fun CategoryTabs(
    modifier: Modifier = Modifier,
    categories: List<String> = listOf(
        Categories.Top.toString(),
        Categories.New.toString(),
        Categories.Best.toString(),
        Categories.Ask.toString(),
        Categories.Show.toString(),
    ),
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val activeColor = Color(0xFF1F3A2E) // Dark forest green
    val inactiveColor = Color(0xFF4A524D) // Muted dark gray

    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = activeColor,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = modifier.tabIndicatorOffset(selectedTabIndex),
                width = LocalTechNewsDimens.current.space4XLarge,
                height = LocalTechNewsDimens.current.spaceExtraSmall,
                color = activeColor,
                shape = RoundedCornerShape(
                    topStart = LocalTechNewsDimens.current.space2XLarge,
                    topEnd = LocalTechNewsDimens.current.space2XLarge
                )
            )
        },
        divider = {
            HorizontalDivider(
                thickness = LocalTechNewsDimens.current.dividerThickness,
                color = Color(0xFFE0E2DE)
            )
        },
    ) {
        categories.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        title,
                        style = TechNewsTypography.bodySmall,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                        color = if (isSelected) activeColor else inactiveColor
                    )
                }
            )
        }
    }
}

@Composable
fun ContentSection(
    modifier: Modifier = Modifier
) {
    val listItem = listOf(
        Item(
            id = 456789,
            time = 1793657000L, // Example Unix timestamp
            deleted = false,
            type = Type.STORY, // Assuming Type enum contains STORY, COMMENT, JOB, etc.
            by = "johndoe",
            text = null,
            dead = false,
            parent = null,
            poll = null,
            kids = listOf(456790, 456795, 456801),
            url = "https://example.com",
            score = 142,
            title = "Why Jetpack Compose is a Game Changer",
            parts = null,
            descendants = 3
        ),
        Item(
            id = 456790,
            time = 1793657500L,
            deleted = null, // Testing null safety
            type = Type.COMMENT,
            by = "tech_lead",
            text = "Completely agree! The declarative UI makes state handling so much cleaner.",
            dead = false,
            parent = 456789, // References item1
            poll = null,
            kids = null,
            url = null,
            score = null,
            title = null,
            parts = null,
            descendants = null
        ),
        Item(
            id = 456810,
            time = 1793659000L,
            deleted = false,
            type = Type.JOB,
            by = "startup_hr",
            text = "We are looking for a Senior Android Engineer in HCMC. Apply via the link!",
            dead = false,
            parent = null,
            poll = null,
            kids = null,
            url = "https://startup.jobs",
            score = 1,
            title = "Amaris Consulting is hiring a Mobile Tech Lead",
            parts = null,
            descendants = null
        ),
    )

    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.height(LocalTechNewsDimens.current.space2XSmall))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.feed_source, StringNote.HardcodedString),
                style = TechNewsTypography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    vertical = LocalTechNewsDimens.current.spaceSmall
                )
            )

            Text(
                stringResource(R.string .cached_story_count, -1),
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                style = TechNewsTypography.labelLarge
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LocalTechNewsDimens.current.spaceSmall),
        ) {
            items(
                items = listItem,
                key = { item -> item.id }
            ) { item ->
                NewsItem(item = item)
            }
        }
    }
}

@Composable
fun AllCatchUpText(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            vertical = LocalTechNewsDimens.current.space2XLarge,
            horizontal = LocalTechNewsDimens.current.spaceMedium
        )
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(0.3f),
            thickness = LocalTechNewsDimens.current.dividerThickness,
            color = Color.LightGray
        )
        Text(
            title,
            style = TechNewsTypography.labelMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
        HorizontalDivider(
            modifier = Modifier.weight(0.3f),
            thickness = LocalTechNewsDimens.current.dividerThickness,
            color = Color.LightGray
        )
    }
}