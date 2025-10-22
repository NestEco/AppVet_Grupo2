package com.example.appvet_grupo2.ui.screens

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarMascotaScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableIntStateOf(1) }
    var especie by remember { mutableStateOf("Perro") }
    var showEdadPicker by remember { mutableStateOf(false) }
    var showEspeciePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val especiesDisponibles = listOf("Perro", "Gato", "Conejo", "Hámster", "Ave", "Reptil", "Otro")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White,
                ),
                title = { Text("Registrar Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Foto de la mascota
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Foto de mascota",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // Aquí iría la lógica para seleccionar foto
                }
            ) {
                Text("Cambiar foto")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la mascota") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de edad
            OutlinedTextField(
                value = "$edad año${if (edad != 1) "s" else ""}",
                onValueChange = { },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showEdadPicker = true }) {
                        Text("▼")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de especie
            OutlinedTextField(
                value = especie,
                onValueChange = { },
                label = { Text("Especie") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showEspeciePicker = true }) {
                        Text("▼")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón registrar
            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        val nuevaMascota = Mascota(
                            nombre = nombre,
                            edad = edad,
                            especie = especie
                        )
                        viewModel.agregarMascota(nuevaMascota)
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank()
            ) {
                Text("Registrar Mascota")
            }
        }

        // Dialog para seleccionar edad
        if (showEdadPicker) {
            AlertDialog(
                onDismissRequest = { showEdadPicker = false },
                confirmButton = {
                    TextButton(onClick = { showEdadPicker = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEdadPicker = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Seleccionar edad") },
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        NumberPicker(
                            value = edad,
                            onValueChange = { edad = it },
                            range = 0..30
                        )
                    }
                }
            )
        }

        // Dialog para seleccionar especie
        if (showEspeciePicker) {
            AlertDialog(
                onDismissRequest = { showEspeciePicker = false },
                confirmButton = {
                    TextButton(onClick = { showEspeciePicker = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEspeciePicker = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Seleccionar especie") },
                text = {
                    Column {
                        especiesDisponibles.forEach { especieOpcion ->
                            TextButton(
                                onClick = {
                                    especie = especieOpcion
                                    showEspeciePicker = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(especieOpcion)
                            }
                        }
                    }
                }
            )
        }

        // Dialog de éxito
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    navController.navigateUp()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            navController.navigateUp()
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Éxito") },
                text = { Text("Mascota registrada con éxito") }
            )
        }
    }
}