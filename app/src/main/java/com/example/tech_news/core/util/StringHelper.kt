package com.example.tech_news.core.util

fun String.stripUrlPrefix(): String {
    return this.removePrefix("https://")
        .removePrefix("http://")
        .removePrefix("www.") // Optional: remove if you want to keep www.
        .trimEnd('/')
}