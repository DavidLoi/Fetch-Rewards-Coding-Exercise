package com.example.fetchrewardscodingexercise

import retrofit2.http.GET

interface ApiService {

    @GET("hiring.json")
    suspend fun fetchListItems(): List<ListItem>
}