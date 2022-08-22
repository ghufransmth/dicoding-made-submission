package com.example.core.utils

import com.example.core.data.source.local.entity.CreatorEntity
import com.example.core.data.source.remote.response.CreatorResponse
import com.example.core.domain.model.Creator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToEntities(input: List<CreatorResponse>): List<CreatorEntity> {
        val gameList = ArrayList<CreatorEntity>()

        input.map {
            val positions = getPositionsName(it)
            val games = getGamesName(it)
            val game = CreatorEntity(
                id = it.id,
                name = it.name,
                slug = it.slug,
                image = it.image ?: "No Data",
                image_background = it.image_background,
                description = it.description ?:"",
                games_count = it.games_count ?: 0,
                reviews_count = it.reviews_count ?: 0,
                rating = it.rating ?:"",
                positions = positions,
                games = games,
                isFavorite = false
            )
            gameList.add(game)
        }
        return gameList
    }

    fun mapEntitiesToDomain(input: List<CreatorEntity>): List<Creator> =
        input.map {
            Creator(
                id = it.id,
                name = it.name,
                slug = it.slug,
                image = it.image,
                image_background = it.image_background.toString(),
                description = it.description,
                games_count = it.games_count,
                reviews_count = it.reviews_count,
                rating = it.rating,
                positions = it.positions,
                games = it.games,
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: Creator): CreatorEntity = CreatorEntity(
        id = input.id,
        name = input.name,
        slug = input.slug,
        image = input.image,
        image_background = input.image_background,
        description = input.description,
        games_count = input.games_count,
        reviews_count = input.reviews_count,
        rating = input.rating,
        positions = input.positions,
        games = input.games,
        isFavorite = input.isFavorite
    )

    fun mapEntityToDomain(input: CreatorEntity): Creator = Creator(
        id = input.id,
        name = input.name,
        slug = input.slug,
        image = input.image,
        image_background = input.image_background.toString(),
        description = input.description,
        games_count = input.games_count,
        reviews_count = input.reviews_count,
        rating = input.rating,
        positions = input.positions,
        games = input.games,
        isFavorite = input.isFavorite
    )

    fun mapResponseToEntity(input: CreatorResponse): CreatorEntity {
        val positions = getPositionsName(input)
        val games = getGamesName(input)

        return CreatorEntity(
            id = input.id,
            name = input.name,
            slug = input.slug,
            image = input.image ?: "No Data",
            image_background = input.image_background,
            description = input.description ?:"",
            games_count = input.games_count ?: 0,
            reviews_count = input.reviews_count ?:0,
            rating = input.rating ?:"",
            positions = positions,
            games = games,
            isFavorite = false
        )
    }

    fun mapResponseToDomain(input: CreatorResponse): Flow<Creator> {
        val positions = getPositionsName(input)
        val games = getGamesName(input)

        return flowOf(
            Creator(
                input.id,
                input.name,
                input.slug,
                ""+input.image,
                ""+input.image_background,
                input.description,
                input.games_count,
                input.reviews_count,
                input.rating,
                positions,
                games,
                false
            )
        )
    }

    private fun getPositionsName(data: CreatorResponse): String {
        val result = StringBuilder().append("")

        if (data.platforms != null) {
            for (i in data.platforms.indices) {
                if (i < data.platforms.size - 1) {
                    result.append("${data.platforms[i].name}, ")
                } else {
                    result.append(data.platforms[i].name)
                }
            }
        }

        return result.toString()
    }

    private fun getGamesName(data: CreatorResponse): String {
        val result = StringBuilder().append("")

        if (data.games != null) {
            for (i in data.games.indices) {
                if (i < data.games.size - 1) {
                    result.append("${data.games[i].name}, ")
                } else {
                    result.append(data.games[i].name)
                }
            }
        }

        return result.toString()
    }
}