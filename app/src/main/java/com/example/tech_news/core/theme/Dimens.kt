package com.example.tech_news.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TechNewsDimens(
    val spaceNone: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 12.dp,
    val spaceLarge: Dp = 16.dp,
    val spaceExtraLarge: Dp = 20.dp,
    val space2XLarge: Dp = 24.dp,
    val space3XLarge: Dp = 32.dp,
    val space4XLarge: Dp = 40.dp,
    val screenHorizontalPadding: Dp,
    val screenVerticalPadding: Dp,
    val cardPadding: Dp,
    val cardSpacing: Dp,
    val sectionSpacing: Dp,
    val minimumTouchTarget: Dp = 48.dp,
    val iconSmall: Dp = 14.dp,
    val iconMedium: Dp = 22.dp,
    val iconLarge: Dp = 30.dp,
    val avatarSmall: Dp = 28.dp,
    val navigationBarHeight: Dp = 80.dp,
    val topAppBarHeight: Dp = 68.dp,
    val storyCardMinHeight: Dp = 154.dp,
    val searchBarHeight: Dp = 58.dp,
    val switchWidth: Dp = 52.dp,
    val switchHeight: Dp = 32.dp,
    val commentIndent: Dp = 16.dp,
    val dividerThickness: Dp = 1.dp,
)

val CompactDimens = TechNewsDimens(
    screenHorizontalPadding = 12.dp,
    screenVerticalPadding = 18.dp,
    cardPadding = 18.dp,
    cardSpacing = 10.dp,
    sectionSpacing = 28.dp,
)

val PhoneLandscapeDimens = TechNewsDimens(
    screenHorizontalPadding = 24.dp,
    screenVerticalPadding = 12.dp,
    cardPadding = 18.dp,
    cardSpacing = 10.dp,
    sectionSpacing = 24.dp,
    storyCardMinHeight = 138.dp,
)

val LocalTechNewsDimens = staticCompositionLocalOf { CompactDimens }
