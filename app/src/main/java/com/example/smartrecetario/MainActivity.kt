package com.example.smartrecetario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.smartrecetario.data.local.AppDatabase
import com.example.smartrecetario.data.local.entity.Receta
import com.example.smartrecetario.ui.theme.SmartRecetarioTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val recetaDao = db.recetaDao()

        setContent {

            SmartRecetarioTheme {

                var recetas by remember { mutableStateOf(listOf<Receta>()) }

                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {

                    scope.launch(Dispatchers.IO) {

                        val resultado = recetaDao.obtenerTodas()

                        recetas = resultado
                    }

                }

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    ListaRecetas(recetas)

                }
            }
        }
    }
}

@Composable
fun ListaRecetas(recetas: List<Receta>) {

    LazyColumn {

        items(recetas) { receta ->

            Text(text = receta.titulo)

        }

    }

}