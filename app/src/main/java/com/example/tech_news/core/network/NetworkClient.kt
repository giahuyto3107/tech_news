package com.example.tech_news.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap

object NetworkClient {
    private val cache = ConcurrentHashMap<String, Retrofit>()

    fun getRetrofit(
        baseUrl: String,
    ): Retrofit {
        return cache.computeIfAbsent(baseUrl) { url ->
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    inline fun <reified T> createService(baseUrl: String): T {
        return getRetrofit(baseUrl).create(T::class.java)
    }
}