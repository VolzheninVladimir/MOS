package com.example.recycleviewapp.data.network
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class RickAndMortyService {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getCharacters(): List<com.example.recycleviewapp.data.network.CharacterResponse> {
        val response: CharactersResult = client.get("https://rickandmortyapi.com/api/character").body()
        return response.results
    }
}
