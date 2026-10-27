package com.example.tech_news.core.data.repository

import com.example.tech_news.core.constants.Endpoints
import com.example.tech_news.core.data.datasource.ItemDataSource
import com.example.tech_news.core.data.model.Item
import com.example.tech_news.core.network.NetworkClient

class ItemRepository(
    private val itemDataSource: ItemDataSource = NetworkClient.createService<ItemDataSource>(baseUrl = Endpoints.baseUrl)
) {
    suspend fun getItem(id: Int) : Item? {
        return itemDataSource.getItemById(id)
    }

    suspend fun getMaxItem(): Int {
        return itemDataSource.getMaxItemID()
    }

    suspend fun getTopStoryIds(): List<Int> {
        return itemDataSource.getTopStoryIds()
    }

    suspend fun getNewStoryIds(): List<Int> {
        return itemDataSource.getNewStoryIds()
    }

    suspend fun getBestStoryIds(): List<Int> {
        return itemDataSource.getBestStoryIds()
    }
}