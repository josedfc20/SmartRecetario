package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.entity.Receta

@Composable
fun ListaRecetasScreen(
    recetas: List<Receta>,
    onRecetaClick: (Receta) -> Unit,
    onEditarReceta: (Receta) -> Unit,
    onNuevaReceta: () -> Unit,
    onFiltrar: () -> Unit,
    onEliminarReceta: (Receta) -> Unit // 🔹 NUEVO
) {

    Column {

        Button(
            onClick = { onNuevaReceta() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Nueva receta")
        }

        Button(
            onClick = { onFiltrar() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Filtrar por presupuesto")
        }

        LazyColumn {

            items(recetas) { receta ->

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    // 🔹 Click para ver detalle
                    Column(
                        modifier = Modifier.clickable {
                            onRecetaClick(receta)
                        }
                    ) {

                        Text(text = receta.titulo)

                        Text(text = receta.descripcion)

                        Text(text = "Tiempo: ${receta.tiempoPreparacion} min")

                        Text(
                            text = "Precio: ${"%.2f".format(receta.costeTotal)} €"
                        )
                    }

                    // BOTÓN ELIMINAR
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Button(
                            onClick = { onEliminarReceta(receta) }
                        ) {
                            Text("Eliminar")
                        }

                        Button(
                            onClick = { onEditarReceta(receta) }
                        ) {
                            Text("Editar")
                        }

                    }

                }

            }

        }

    }

}