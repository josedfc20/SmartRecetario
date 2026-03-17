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
    recetaExistente: Receta? = null, // 🔹 NUEVO
    onGuardar: (Receta) -> Unit,
    onVolver: () -> Unit
) {

    // 🔹 Si hay receta → precargar datos
    var titulo by remember { mutableStateOf(recetaExistente?.titulo ?: "") }
    var descripcion by remember { mutableStateOf(recetaExistente?.descripcion ?: "") }
    var tiempo by remember { mutableStateOf(recetaExistente?.tiempoPreparacion?.toString() ?: "") }
    var precio by remember { mutableStateOf(recetaExistente?.costeTotal?.toString() ?: "") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = if (recetaExistente == null) "Nueva receta" else "Editar receta"
        )

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
                    idReceta = recetaExistente?.idReceta ?: 0, // 🔹 CLAVE
                    titulo = titulo,
                    descripcion = descripcion,
                    tiempoPreparacion = tiempo.toIntOrNull() ?: 0,
                    costeTotal = precio.toDoubleOrNull() ?: 0.0,
                    idCategoria = 1
                )

                onGuardar(receta)

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text(
                if (recetaExistente == null) "Guardar receta"
                else "Actualizar receta"
            )

        }

        Button(
            onClick = { onVolver() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver")
        }

    }

}