package com.example.tech_news.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.InsertComment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tech_news.core.app_enum.StringNote
import com.example.tech_news.core.item.data.model.Item
import com.example.tech_news.core.theme.LightOnSurface
import com.example.tech_news.core.theme.LightOnSurfaceVariant
import com.example.tech_news.core.theme.LightOutline
import com.example.tech_news.core.theme.LightPrimaryContainer
import com.example.tech_news.core.theme.LocalTechNewsDimens
import com.example.tech_news.core.theme.TechNewsGreen
import com.example.tech_news.core.theme.TechNewsGreenContainer
import com.example.tech_news.core.theme.TechNewsGreenDark
import com.example.tech_news.core.theme.TechNewsGreenLight
import com.example.tech_news.core.theme.TechNewsShapes
import com.example.tech_news.core.theme.TechNewsTypography
import com.example.tech_news.core.util.stripUrlPrefix

@Composable
fun NewsItem(
    item: Item
) {
    Box(
        modifier = Modifier
            .border(
                width = LocalTechNewsDimens.current.space2XSmall,
                color = LightOutline,
                shape = TechNewsShapes.small
            )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = LocalTechNewsDimens.current.spaceMedium,
                vertical = LocalTechNewsDimens.current.spaceMedium,
            )
        ) {
            Status(
                deleted = item.deleted,
                url = item.url,
                modifier = Modifier.fillMaxWidth()
            )
            Title(
                title = item.title,
                modifier = Modifier.fillMaxWidth()
            )
            Details(
                author = item.by,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Status(
    url: String?,
    deleted: Boolean?,
    modifier: Modifier = Modifier,
) {
    when(deleted) {
        true -> Box() {
            Text("Deleted")
        }

        false -> Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(LocalTechNewsDimens.current.spaceMedium)
                        .clip(CircleShape)
                        .background(TechNewsGreenDark)
                )
                Spacer(modifier = Modifier.width(LocalTechNewsDimens.current.spaceSmall))
                Box(
                    modifier = Modifier
                        .clip(
                            TechNewsShapes.small
                        )
                        .background(TechNewsGreenContainer)
                ) {
                    Text(
                        url?.stripUrlPrefix() ?: "Unknown",
                        color = TechNewsGreen,
                        style = TechNewsTypography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            horizontal = LocalTechNewsDimens.current.spaceSmall,
                            vertical = LocalTechNewsDimens.current.space2XSmall,
                        )
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Bookmark"
            )
        }

        null -> Box() {
            Text("Deleted")
        }

    }
}

@Composable
private fun Title(
    title: String?,
    modifier: Modifier = Modifier
) {
    Text(
        title ?: "Unknown",
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

@Composable
private fun Details(
    author: String?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Upvotes
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Upvotes",
            tint = Color.Gray,
        )
        //        Hardcoded
        Text(
            "-1",
            fontWeight = FontWeight.ExtraLight,
            style = TechNewsTypography.labelMedium
        )
        Spacer(modifier = Modifier.width(LocalTechNewsDimens.current.spaceMedium))
        // Comments
        Icon(
            imageVector = Icons.AutoMirrored.Filled.InsertComment,
            contentDescription = "Upvote",
            tint = Color.Gray,
            modifier = Modifier.size(LocalTechNewsDimens.current.iconSmall)
        )
        Spacer(modifier = Modifier.width(LocalTechNewsDimens.current.spaceMedium))
        //        Hardcoded
        Text(
            "-1",
            fontWeight = FontWeight.ExtraLight,
            style = TechNewsTypography.labelMedium
        )
        Spacer(modifier = Modifier.width(LocalTechNewsDimens.current.spaceMedium))
        // Author
        Text(
            author ?: "Unknown",
            fontWeight = FontWeight.ExtraLight,
            style = TechNewsTypography.labelMedium
        )
        Spacer(modifier = Modifier.width(LocalTechNewsDimens.current.spaceMedium))
//        Hardcoded
        Text(
            "-1h",
            fontWeight = FontWeight.ExtraLight,
            style = TechNewsTypography.labelMedium
        )
    }
}
