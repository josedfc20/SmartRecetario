package com.example.smartrecetario.data.local

import androidx.room.*
import com.example.smartrecetario.data.local.entity.Receta

@Dao
interface RecetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(receta: Receta)

    @Query("SELECT * FROM recetas")
    suspend fun obtenerTodas(): List<Receta>

    @Delete
    suspend fun eliminar(receta: Receta)

    @Update
    suspend fun actualizar(receta: Receta)
}