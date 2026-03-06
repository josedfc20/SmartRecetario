package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.entity.Receta

@Composable
fun NuevaRecetaScreen(
    onGuardar: (Receta) -> Unit
) {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Nueva receta")

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") }
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") }
        )

        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo preparación (min)") }
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio estimado (€)") }
        )

        Button(
            onClick = {

                val receta = Receta(
                    titulo = titulo,
                    descripcion = descripcion,
                    tiempoPreparacion = tiempo.toInt(),
                    costeTotal = precio.toDouble(),
                    idCategoria = 1
                )

                onGuardar(receta)

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text("Guardar receta")

        }

    }

}