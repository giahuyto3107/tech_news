package com.example.tech_news.core.user.repository

import com.example.tech_news.core.constants.Endpoints
import com.example.tech_news.core.network.NetworkClient
import com.example.tech_news.core.user.datasource.UserDataSource
import com.example.tech_news.core.user.model.User

class UserRepository(
    private val userDataSource: UserDataSource = NetworkClient.createService<UserDataSource>(
        Endpoints.baseUrl)
) {
    suspend fun getUser(username: String): User? {
        return userDataSource.getUserById(username)
    }
}