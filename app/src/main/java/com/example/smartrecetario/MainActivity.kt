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
import com.example.smartrecetario.ui.screens.DetalleRecetaScreen
import com.example.smartrecetario.ui.screens.FiltroPresupuestoScreen
import com.example.smartrecetario.ui.screens.ListaRecetasScreen
import com.example.smartrecetario.ui.screens.NuevaRecetaScreen
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

                        "detalle" -> {

                            DetalleRecetaScreen(
                                receta = recetaSeleccionada!!,
                                onVolver = {
                                    pantallaActual = "lista"
                                }
                            )

                        }

                        "nueva" -> {

                            NuevaRecetaScreen { nuevaReceta ->

                                scope.launch(Dispatchers.IO) {

                                    recetaDao.insertar(nuevaReceta)

                                    recetas = recetaDao.obtenerTodas()

                                    pantallaActual = "lista"

                                }

                            }

                        }

                        "filtro" -> {

                            FiltroPresupuestoScreen { presupuesto ->

                                scope.launch(Dispatchers.IO) {

                                    val resultado = recetaDao.obtenerRecetasPorPresupuesto(presupuesto)

                                    recetas = resultado

                                    pantallaActual = "lista"

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}