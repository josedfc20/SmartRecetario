package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.entity.Receta

@Composable
fun NuevaRecetaScreen(
    onGuardar: (Receta) -> Unit,
    onVolver: () -> Unit
){

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    var errorMensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Nueva receta")

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo preparación (min)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio estimado (€)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        if (errorMensaje.isNotEmpty()) {
            Text(
                text = errorMensaje,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {

                val tiempoInt = tiempo.toIntOrNull()
                val precioDouble = precio.toDoubleOrNull()

                if (titulo.isBlank() || descripcion.isBlank()) {

                    errorMensaje = "Completa todos los campos"

                } else if (tiempoInt == null) {

                    errorMensaje = "El tiempo debe ser un número"

                } else if (precioDouble == null) {

                    errorMensaje = "El precio debe ser un número"

                } else {

                    val receta = Receta(
                        titulo = titulo,
                        descripcion = descripcion,
                        tiempoPreparacion = tiempoInt,
                        costeTotal = precioDouble,
                        idCategoria = 1
                    )

                    onGuardar(receta)

                    errorMensaje = ""
                }

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text("Guardar receta")

        }

        Button(
            onClick = { onVolver() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver")
        }

    }

}