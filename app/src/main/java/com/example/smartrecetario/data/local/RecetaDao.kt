package com.example.smartrecetario.data.local

import androidx.room.*
import com.example.smartrecetario.data.local.entity.Receta
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReceta(receta: Receta)

    @Query("SELECT * FROM recetas")
    fun obtenerTodas(): Flow<List<Receta>>

    @Query("SELECT * FROM recetas WHERE costeTotal <= :presupuesto")
    suspend fun obtenerRecetasPorPresupuesto(presupuesto: Double): List<Receta>

    @Query("UPDATE recetas SET costeTotal = :coste WHERE idReceta = :id")
    suspend fun actualizarCoste(id: Int, coste: Double)

    @Delete
    suspend fun eliminar(receta: Receta)

    @Update
    suspend fun actualizar(receta: Receta)
}