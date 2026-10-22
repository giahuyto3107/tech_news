package com.example.tech_news.core.data.datasource

import com.example.tech_news.core.data.model.Item
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemDataSource {
    @GET("item/{id}.json")
    suspend fun getItemById(
        @Path("id") itemId: Int
    ): Item?

    @GET("maxitem.json")
    suspend fun getMaxItemID(): Int

    @GET("topstories.json")
    suspend fun getTopStoryIds(): List<Int>

    @GET("newstories.json")
    suspend fun getNewStoryIds(): List<Int>

    @GET("beststories.json")
    suspend fun getBestStoryIds(): List<Int>

//    @GET("askstories.json")
//    suspend fun getAskStories(): List<Int>
}