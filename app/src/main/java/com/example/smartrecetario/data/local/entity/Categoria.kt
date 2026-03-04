package com.example.smartrecetario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class Categoria(

    @PrimaryKey(autoGenerate = true)
    val idCategoria: Int = 0,

    val nombre: String
)