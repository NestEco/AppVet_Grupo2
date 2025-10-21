package com.example.appvet_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appvet_grupo2.navigation.Screen
import com.example.appvet_grupo2.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservarScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Elegir tipo de Agenda") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //Aquí sin importar cual se escoga, llevará a la pantalla FechaScreen, siendo la diferencia
            //la variable que se almacena.
            Button(onClick = {viewModel.navigateTo(Screen.SelectFecha)}) {
                Text("Consulta")
            }
            Button(onClick = {viewModel.navigateTo(Screen.SelectFecha)}) {
                Text("Vacunacion y Deparacitacion")
            }
            Button(onClick = {viewModel.navigateTo(Screen.SelectFecha)}) {
                Text("Cirugia y Procesos Especializados")
            }
        }
    }
}