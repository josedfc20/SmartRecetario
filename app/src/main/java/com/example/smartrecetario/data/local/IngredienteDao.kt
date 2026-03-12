package com.example.smartrecetario.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartrecetario.data.local.entity.Ingrediente
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredienteDao {

    @Query("SELECT * FROM ingredientes WHERE idReceta = :recetaId")
    fun obtenerIngredientesPorReceta(recetaId: Int): Flow<List<Ingrediente>>

    @Insert
    suspend fun insertarIngrediente(ingrediente: Ingrediente)

}