package com.example.smartrecetario.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartrecetario.data.local.entity.Categoria
import com.example.smartrecetario.data.local.entity.Ingrediente
import com.example.smartrecetario.data.local.entity.Receta

@Database(
    entities = [Receta::class, Categoria::class, Ingrediente::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recetaDao(): RecetaDao
    abstract fun categoriaDao(): CategoriaDao

    abstract fun ingredienteDao(): IngredienteDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartrecetario_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}