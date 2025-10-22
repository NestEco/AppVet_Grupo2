package com.example.appvet_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
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
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoraScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    appState: AppState
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showTimePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf<Int?>(null) }
    var selectedMinute by remember { mutableStateOf<Int?>(null) }

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Selección de Hora")
                }
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
            // Mostrar información de la agenda
            Text("Tipo: ${viewModel.tipoAgendaTemp}")

            Spacer(modifier = Modifier.height(8.dp))

            if (viewModel.fechaTemp != null) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateString = formatter.format(Date(viewModel.fechaTemp!!))
                Text("Fecha: $dateString")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { showTimePicker = true }
            ) {
                Text("Seleccionar Hora")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedHour != null && selectedMinute != null) {
                val hourFormatted = String.format("%02d", selectedHour)
                val minuteFormatted = String.format("%02d", selectedMinute)
                Text("Hora seleccionada: $hourFormatted:$minuteFormatted")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.setHora(selectedHour!!, selectedMinute!!)
                    viewModel.agregarHoraAgendada()
                    showSuccessDialog = true
                }) {
                    Text("Confirmar Agenda")
                }
            } else {
                Text("No se ha seleccionado ninguna hora")
            }
        }

        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                            showTimePicker = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showTimePicker = false }
                    ) {
                        Text("Cancelar")
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    navController.navigate("agenda")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            navController.navigate("agenda")
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                title = {
                    Text("Éxito")
                },
                text = {
                    Text("Hora agendada con éxito")
                }
            )
        }
    }
}