package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
    onNuevaReceta: () -> Unit
) {

    Column {

        Button(
            onClick = { onNuevaReceta() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Nueva receta")
        }

        LazyColumn {

            items(recetas) { receta ->

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onRecetaClick(receta)
                        }
                ) {

                    Text(text = receta.titulo)

                    Text(text = receta.descripcion)

                    Text(text = "Tiempo: ${receta.tiempoPreparacion} min")

                    Text(text = "Precio: ${receta.costeTotal} €")

                }

            }

        }

    }

}