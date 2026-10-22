package com.example.tech_news.core.data.model

import com.example.tech_news.core.constants.Type
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Int, // The item's unique id.

    @SerializedName("time")
    val time: Long, // Creation date of the item, in Unix Time.

    @SerializedName("deleted")
    val deleted: Boolean?, // returns True if item is deleted

    @SerializedName("type")
    val type: Type?, // The type of item. One of "job", "story", "comment", "poll", or "pollopt".

    @SerializedName("by")
    val by: String?, // The username of the item's author.

    @SerializedName("text")
    val text: String?, // The comment, story or poll text. HTML.

    @SerializedName("dead")
    val dead: Boolean?, // returns True if item is dead

    @SerializedName("parent")
    val parent: Int?, // The comment's parent: either another comment or the relevant story.

    @SerializedName("poll")
    val poll: Int?, // The pollopt's associated poll.

    @SerializedName("kids")
    val kids: List<Int>?, // The ids of the item's comments, in ranked display order.

    @SerializedName("url")
    val url: String?, // The url of the story

    @SerializedName("score")
    val score: Int?, // The story's score, or the votes for a pollopt.

    @SerializedName("title")
    val title: String?, // The title of the story, poll or job. HTML.

    @SerializedName("parts")
    val parts: List<Int>?, // A list of related pollopts, in display order.

    @SerializedName("descendants")
    val descendants: Int?, // In the case of stories or polls, the total comment count.
)