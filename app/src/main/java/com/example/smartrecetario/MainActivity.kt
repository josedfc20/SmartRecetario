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
import com.example.smartrecetario.data.local.entity.Ingrediente
import com.example.smartrecetario.ui.screens.*
import com.example.smartrecetario.ui.theme.SmartRecetarioTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val recetaDao = db.recetaDao()

        setContent {

            SmartRecetarioTheme {

                // 🔹 Flow de recetas (reactivo)
                val recetas by recetaDao
                    .obtenerTodas()
                    .collectAsState(initial = emptyList())

                // 🔹 Filtro
                var recetasFiltradas by remember { mutableStateOf<List<Receta>?>(null) }
                val listaAMostrar = recetasFiltradas ?: recetas

                var recetaSeleccionada by remember { mutableStateOf<Receta?>(null) }
                var pantallaActual by remember { mutableStateOf("lista") }

                val scope = rememberCoroutineScope()

                var ingredienteSeleccionado by remember { mutableStateOf<Ingrediente?>(null) }

                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (pantallaActual) {

                        // =========================
                        // LISTA
                        // =========================
                        "lista" -> {

                            ListaRecetasScreen(
                                recetas = listaAMostrar,

                                onRecetaClick = {
                                    recetaSeleccionada = it
                                    pantallaActual = "detalle"
                                },

                                onNuevaReceta = {
                                    recetasFiltradas = null
                                    pantallaActual = "nueva"
                                },

                                onFiltrar = {
                                    pantallaActual = "filtro"
                                },

                                onEliminarReceta = { receta ->

                                    scope.launch(Dispatchers.IO) {
                                        recetaDao.eliminar(receta)
                                    }

                                },

                                onEditarReceta = { receta ->
                                    recetaSeleccionada = receta
                                    pantallaActual = "editar"
                                }

                            )
                        }

                        // =========================
                        // NUEVA RECETA
                        // =========================
                        "nueva" -> {

                            NuevaRecetaScreen(

                                onGuardar = { receta ->

                                    scope.launch(Dispatchers.IO) {
                                        recetaDao.insertarReceta(receta)
                                    }

                                    pantallaActual = "lista"
                                },

                                onVolver = {
                                    pantallaActual = "lista"
                                }

                            )

                        }

                        // =========================
                        // EDITAR RECETA
                        // =========================
                        "editar" -> {

                            NuevaRecetaScreen(

                                recetaExistente = recetaSeleccionada,

                                onGuardar = { receta ->

                                    scope.launch(Dispatchers.IO) {
                                        recetaDao.actualizar(receta)
                                    }

                                    pantallaActual = "lista"
                                },

                                onVolver = {
                                    pantallaActual = "lista"
                                }

                            )

                        }

                        // =========================
                        // DETALLE
                        // =========================
                        "detalle" -> {

                            DetalleRecetaScreen(
                                receta = recetaSeleccionada!!,
                                db = db,
                                onVolver = { pantallaActual = "lista" },
                                onAgregarIngrediente = { pantallaActual = "agregarIngrediente" },

                                onEditarIngrediente = { ingrediente ->
                                    ingredienteSeleccionado = ingrediente
                                    pantallaActual = "editarIngrediente"
                                }

                            )
                        }

                        // =========================
                        // AGREGAR INGREDIENTE
                        // =========================
                        "agregarIngrediente" -> {

                            AgregarIngredienteScreen(
                                idReceta = recetaSeleccionada!!.idReceta,

                                onGuardar = { ingrediente ->

                                    scope.launch(Dispatchers.IO) {
                                        db.ingredienteDao().insertarIngrediente(ingrediente)
                                    }

                                    pantallaActual = "detalle"
                                },

                                onVolver = {
                                    pantallaActual = "detalle"
                                }
                            )
                        }

                        // =========================
                        // EDITAR INGREDIENTES
                        // =========================

                        "editarIngrediente" -> {

                            AgregarIngredienteScreen(
                                idReceta = recetaSeleccionada!!.idReceta,
                                ingredienteExistente = ingredienteSeleccionado,

                                onGuardar = { ingrediente ->

                                    scope.launch(Dispatchers.IO) {
                                        db.ingredienteDao().insertarIngrediente(ingrediente)
                                    }

                                    pantallaActual = "detalle"
                                },

                                onVolver = {
                                    pantallaActual = "detalle"
                                }

                            )

                        }

                        // =========================
                        // FILTRO
                        // =========================
                        "filtro" -> {

                            FiltroPresupuestoScreen(

                                onBuscar = { presupuesto ->

                                    scope.launch(Dispatchers.IO) {

                                        val filtradas =
                                            recetaDao.obtenerRecetasPorPresupuesto(presupuesto)

                                        recetasFiltradas = filtradas
                                        pantallaActual = "lista"
                                    }

                                },

                                onVolver = {
                                    pantallaActual = "lista"
                                }

                            )

                        }

                    }

                }

            }

        }

    }

}