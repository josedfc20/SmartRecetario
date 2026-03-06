package com.example.smartrecetario.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartrecetario.data.local.entity.Receta

@Composable
fun DetalleRecetaScreen(
    receta: Receta,
    onVolver: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = receta.titulo)

        Text(text = receta.descripcion)

        Text(text = "Tiempo: ${receta.tiempoPreparacion} min")

        Text(text = "Precio estimado: ${receta.costeTotal} €")

        Button(
            onClick = { onVolver() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Volver")
        }

    }

}