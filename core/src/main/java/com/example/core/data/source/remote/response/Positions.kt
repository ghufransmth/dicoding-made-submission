package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Positions (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("slug")
    val slug: String,

)