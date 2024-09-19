package com.example.fetchrewardscodingexercise

import com.google.gson.annotations.SerializedName

data class ListItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("listId")
    val listId: Int,

    @SerializedName("name")
    val name: String
)
