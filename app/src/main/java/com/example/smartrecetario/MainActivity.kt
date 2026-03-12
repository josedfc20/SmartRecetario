package com.example.smartrecetario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.smartrecetario.data.local.AppDatabase
import com.example.smartrecetario.data.local.entity.Receta
import com.example.smartrecetario.ui.screens.*
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

                var recetaSeleccionada by remember { mutableStateOf<Receta?>(null) }

                var pantallaActual by remember { mutableStateOf("lista") }

                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {

                    scope.launch(Dispatchers.IO) {

                        recetas = recetaDao.obtenerTodas()

                    }

                }

                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (pantallaActual) {

                        "lista" -> {

                            ListaRecetasScreen(
                                recetas = recetas,

                                onRecetaClick = {
                                    recetaSeleccionada = it
                                    pantallaActual = "detalle"
                                },

                                onNuevaReceta = {
                                    pantallaActual = "nueva"
                                },

                                onFiltrar = {
                                    pantallaActual = "filtro"
                                }
                            )
                        }

                        "nueva" -> {

                            NuevaRecetaScreen(

                                onGuardar = { receta ->

                                    scope.launch(Dispatchers.IO) {

                                        recetaDao.insertarReceta(receta)

                                        recetas = recetaDao.obtenerTodas()

                                        pantallaActual = "lista"

                                    }

                                }

                            )

                        }

                        "detalle" -> {

                            DetalleRecetaScreen(
                                receta = recetaSeleccionada!!,
                                db = db,
                                onVolver = { pantallaActual = "lista" },
                                onAgregarIngrediente = { pantallaActual = "agregarIngrediente" }
                            )
                        }

                        "agregarIngrediente" -> {

                            AgregarIngredienteScreen(
                                idReceta = recetaSeleccionada!!.idReceta,

                                onGuardar = { ingrediente ->

                                    scope.launch(Dispatchers.IO) {

                                        db.ingredienteDao().insertarIngrediente(ingrediente)

                                        pantallaActual = "detalle"

                                    }

                                },

                                onVolver = {
                                    pantallaActual = "detalle"
                                }
                            )
                        }

                    }

                }

            }

        }

    }

}