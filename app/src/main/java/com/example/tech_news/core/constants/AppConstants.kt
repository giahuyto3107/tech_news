package com.example.tech_news.core.constants

import com.google.gson.annotations.SerializedName

enum class Type {
    @SerializedName("job")
    JOB,

    @SerializedName("story")
    STORY,

    @SerializedName("comment")
    COMMENT,

    @SerializedName("poll")
    POLL,

    @SerializedName("pollopt")
    POLLOPT
}