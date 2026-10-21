package com.example.tech_news.core.data.model

data class User(
    val id: Int,
    val created: Long,
    val karma: Int?, // The user's karma.
    val about: String?, // The user's optional self-description. HTML.
    val submitted: String?, // List of the user's stories, polls and comments.
)
