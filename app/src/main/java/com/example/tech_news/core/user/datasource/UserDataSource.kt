package com.example.tech_news.core.user.datasource

import com.example.tech_news.core.user.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDataSource {
    @GET("user/{username}.json")
    suspend fun getUserById(
        @Path("username") username: String
    ): User?
}