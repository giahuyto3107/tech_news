package com.example.tech_news.core.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String, // The user's unique username. Case-sensitive. Required.

    @SerializedName("created")
    val created: Long, // Creation date of the user, in Unix Time.

    @SerializedName("karma")
    val karma: Int?, // The user's karma.

    @SerializedName("about")
    val about: String?, // The user's optional self-description. HTML.

    @SerializedName("submitted")
    val submitted: List<Int>?, // List of the user's stories, polls and comments.
)