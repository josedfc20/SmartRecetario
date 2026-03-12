package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.AppDatabase
import com.example.smartrecetario.data.local.entity.Receta

@Composable
fun DetalleRecetaScreen(
    receta: Receta,
    db: AppDatabase,
    onVolver: () -> Unit,
    onAgregarIngrediente: () -> Unit
) {

    val ingredientes by db
        .ingredienteDao()
        .obtenerIngredientesPorReceta(receta.idReceta)
        .collectAsState(initial = emptyList())

    val costeCalculado = ingredientes.sumOf {
        it.precio * it.cantidad
    }

    // Actualiza el coste total en la base de datos cuando cambie
    LaunchedEffect(costeCalculado) {
        db.recetaDao().actualizarCoste(receta.idReceta, costeCalculado)
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = receta.titulo)

        Text(text = receta.descripcion)

        Text(text = "Tiempo preparación: ${receta.tiempoPreparacion} min")

        Text(text = "Coste guardado: ${receta.costeTotal} €")

        Text(text = "Coste calculado: $costeCalculado €")

        Text(
            text = "Ingredientes",
            modifier = Modifier.padding(top = 16.dp)
        )

        LazyColumn {

            items(ingredientes) { ingrediente ->

                Text(
                    text = "${ingrediente.nombre} - ${ingrediente.cantidad} x ${ingrediente.precio} €"
                )

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