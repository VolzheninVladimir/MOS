package com.example.recycleviewapp.data.repository

import com.example.recycleviewapp.data.db.CharacterDao
import com.example.recycleviewapp.data.db.CharacterEntity
import com.example.recycleviewapp.data.network.CharacterResponse
import com.example.recycleviewapp.data.network.RickAndMortyService
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val service: RickAndMortyService,
    private val dao: CharacterDao
) {
    val characters: Flow<List<CharacterEntity>> = dao.getAll()

    suspend fun refresh() {
        val response: List<CharacterResponse> = service.getCharacters()
        val entities = response.map {
            CharacterEntity(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                image = it.image
            )
        }
        dao.insertAll(entities)
    }
}

