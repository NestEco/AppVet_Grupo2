package com.example.appvet_grupo2.ui.screens

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appvet_grupo2.navigation.Screen
import com.example.appvet_grupo2.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
){
    val scope = rememberCoroutineScope()

    // Estado para controlar la visibilidad del DatePicker
    var showDatePicker by remember { mutableStateOf(false) }

    // Estado para almacenar la fecha seleccionada
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    // Estado del DatePicker
    val datePickerState = rememberDatePickerState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White
                ),
                title = { Text("Seleccionar Fecha")},
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botón para mostrar el DatePicker
            OutlinedButton(
                onClick = { showDatePicker = true }
            ) {
                Text("Seleccionar Fecha")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la fecha seleccionada
            if (selectedDate != null) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateString = formatter.format(Date(selectedDate!!))
                Text("Fecha seleccionada: $dateString")
                //Botón continuar
                Button(onClick = { viewModel.navigateTo(Screen.SelectHora)}) {
                    Text("Continuar")
                }
            } else {
                Text("No se ha seleccionado ninguna fecha")
            }


        }

        // DatePicker bonito
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis
                            showDatePicker = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}