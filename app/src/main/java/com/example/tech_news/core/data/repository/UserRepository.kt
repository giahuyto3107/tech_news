package com.example.tech_news.core.data.repository

import com.example.tech_news.core.constants.Endpoints
import com.example.tech_news.core.data.datasource.UserDataSource
import com.example.tech_news.core.data.model.User
import com.example.tech_news.core.network.NetworkClient

class UserRepository(
    private val userDataSource: UserDataSource = NetworkClient.createService<UserDataSource>(Endpoints.baseUrl)
) {
    suspend fun getUser(username: String): User? {
        return userDataSource.getUserById(username)
    }
}