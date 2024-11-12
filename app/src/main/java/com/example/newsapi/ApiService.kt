package com.example.newsapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines/category/health/in.json")
    fun getHealthNews(): Call<News>
}
