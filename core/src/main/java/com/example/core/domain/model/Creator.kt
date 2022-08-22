package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(
    val id : Int,
    val name : String,
    var slug : String,
    val image : String,
    val image_background : String,
    val description : String,
    val games_count : Int,
    val reviews_count : Int,
    val rating : String,
    val positions : String,
    val games : String,
    var isFavorite: Boolean
) : Parcelable