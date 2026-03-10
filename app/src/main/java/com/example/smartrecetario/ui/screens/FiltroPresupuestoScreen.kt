package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FiltroPresupuestoScreen(
    onBuscar: (Double) -> Unit
) {

    var presupuesto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Filtrar recetas por presupuesto")

        OutlinedTextField(
            value = presupuesto,
            onValueChange = { presupuesto = it },
            label = { Text("Presupuesto (€)") }
        )

        Button(
            onClick = {

                val valor = presupuesto.toDoubleOrNull()

                if (valor != null) {
                    onBuscar(valor)
                }

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text("Buscar recetas")

        }

    }

}