package com.example.smartrecetario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "ingredientes",
    foreignKeys = [
        ForeignKey(
            entity = Receta::class,
            parentColumns = ["idReceta"],
            childColumns = ["idReceta"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Ingrediente(

    @PrimaryKey(autoGenerate = true)
    val idIngrediente: Int = 0,

    val nombre: String,
    val precio: Double,

    val idReceta: Int
)