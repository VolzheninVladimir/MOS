package com.example.recycleviewapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("image") val image: String
)

@Serializable
data class CharactersResult(
    @SerialName("results") val results: List<CharacterResponse>
)
