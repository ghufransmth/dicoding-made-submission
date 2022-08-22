package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_creator")
data class CreatorEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "slug")
    val slug : String,

    @ColumnInfo(name = "image")
    val image : String,

    @ColumnInfo(name = "image_background")
    val image_background : String?,

    @ColumnInfo(name = "description")
    val description : String,

    @ColumnInfo(name = "games_count")
    val games_count : Int,

    @ColumnInfo(name = "reviews_count")
    val reviews_count : Int,

    @ColumnInfo(name = "rating")
    val rating : String,

    @ColumnInfo(name = "positions")
    val positions : String,

    @ColumnInfo(name = "games")
    val games : String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)