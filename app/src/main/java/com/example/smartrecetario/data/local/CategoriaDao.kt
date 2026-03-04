package com.example.smartrecetario.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartrecetario.data.local.entity.Categoria

@Dao
interface CategoriaDao {

    @Insert
    suspend fun insertar(categoria: Categoria)

    @Query("SELECT * FROM categorias")
    suspend fun obtenerTodas(): List<Categoria>
}