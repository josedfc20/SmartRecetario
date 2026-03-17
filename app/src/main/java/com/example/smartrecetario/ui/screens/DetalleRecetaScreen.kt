package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.AppDatabase
import com.example.smartrecetario.data.local.entity.Ingrediente
import com.example.smartrecetario.data.local.entity.Receta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DetalleRecetaScreen(
    receta: Receta,
    db: AppDatabase,
    onVolver: () -> Unit,
    onAgregarIngrediente: () -> Unit,
    onEditarIngrediente: (Ingrediente) -> Unit
) {

    val scope = rememberCoroutineScope()

    val ingredientes by db
        .ingredienteDao()
        .obtenerIngredientesPorReceta(receta.idReceta)
        .collectAsState(initial = emptyList())

    val costeCalculado = ingredientes.sumOf {
        it.precio * it.cantidad
    }

    LaunchedEffect(costeCalculado) {
        scope.launch(Dispatchers.IO) {
            db.recetaDao().actualizarCoste(receta.idReceta, costeCalculado)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = receta.titulo)

        Text(text = receta.descripcion)

        Text(text = "Tiempo preparación: ${receta.tiempoPreparacion} min")

        Text(text = "Coste calculado: ${"%.2f".format(costeCalculado)} €")

        Text(
            text = "Ingredientes",
            modifier = Modifier.padding(top = 16.dp)
        )

        LazyColumn {

            items(ingredientes) { ingrediente ->

                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {

                    Text(
                        text = "${ingrediente.nombre} - ${ingrediente.cantidad} x ${ingrediente.precio} €"
                    )

                    Button(
                        onClick = { onEditarIngrediente(ingrediente) },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Editar")
                    }

                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                db.ingredienteDao().eliminarIngrediente(ingrediente)
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Eliminar")
                    }

                }

            }

        }

        Button(
            onClick = onAgregarIngrediente,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Añadir ingrediente")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver")
        }

    }
}