package com.example.tech_news.core.data.model

import com.example.tech_news.core.constants.Type


data class Item(
    val id: Int,
    val time: Long,
    val deleted: Boolean = false, // returns True if item is deleted
    val type: Type?,
    val by: String?, // The username of the item's author.
    val text: String?, // The comment
    val dead: Boolean = false, // returns True if item is dead
    val parent: Int?, // The comment's parent: either another comment or the relevant story.
    val poll: Int?, // The pollopt's associated poll.
    val kids: List<Int>?, // The ids of the item's comments, in ranked display order.
    val url: String?, // The url of the story
    val score: Double?, // The story's score, or the votes for a pollopt.
    val title: String?,
    val parts: List<Int>?, // A list of related pollopts, in display order.
    val descendants: Int?, // In the case of stories or polls, the total comment count.
)