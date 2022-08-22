package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreatorResponse (

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("image_background")
    val image_background: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("games_count")
    val games_count: Int,
    @SerializedName("reviews_count")
    val reviews_count: Int,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("positions")
    val platforms: List<Positions>?,
    @SerializedName("games")
    val games: List<Games>?,

)