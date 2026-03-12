package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.entity.Ingrediente

@Composable
fun AgregarIngredienteScreen(
    idReceta: Int,
    onGuardar: (Ingrediente) -> Unit,
    onVolver: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Añadir ingrediente")

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre ingrediente") }
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio (€)") }
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") }
        )

        Button(
            onClick = {

                val ingrediente = Ingrediente(
                    nombre = nombre,
                    precio = precio.toDouble(),
                    cantidad = cantidad.toDouble(),
                    idReceta = idReceta
                )

                onGuardar(ingrediente)

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text("Guardar ingrediente")

        }

        Button(
            onClick = { onVolver() },
            modifier = Modifier.padding(top = 8.dp)
        ) {

            Text("Volver")

        }

    }

}