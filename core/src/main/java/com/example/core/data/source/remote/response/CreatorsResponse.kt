package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreatorsResponse (
    @SerializedName("results")
    val results: List<CreatorResponse>
)