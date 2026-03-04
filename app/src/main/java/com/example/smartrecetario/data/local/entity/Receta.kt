package com.example.smartrecetario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "recetas",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["idCategoria"],
            childColumns = ["idCategoria"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Receta(

    @PrimaryKey(autoGenerate = true)
    val idReceta: Int = 0,

    val titulo: String,
    val descripcion: String,
    val tiempoPreparacion: Int,
    val costeTotal: Double,

    val idCategoria: Int
)