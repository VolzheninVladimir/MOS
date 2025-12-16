package com.example.recycleviewapp.domain

import com.example.recycleviewapp.data.db.CharacterEntity

sealed class RecyclerExampleData {
    data class Title(val text: String) : RecyclerExampleData()
    data class Item(val character: CharacterEntity) : RecyclerExampleData()
}
