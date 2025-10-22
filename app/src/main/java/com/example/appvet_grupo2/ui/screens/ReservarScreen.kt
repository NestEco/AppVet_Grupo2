package com.example.appvet_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appvet_grupo2.navigation.Screen
import com.example.appvet_grupo2.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservarScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White
                ),
                title = { Text("Elegir tipo de Agenda") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.setTipoAgenda("Consulta")
                    viewModel.navigateTo(Screen.SelectFecha)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AB66),
                    contentColor = Color.White
                )
            ) {
                Text("Consulta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.setTipoAgenda("Vacunación y Desparasitación")
                    viewModel.navigateTo(Screen.SelectFecha)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AB66),
                    contentColor = Color.White
                )
            ) {
                Text("Vacunación y Desparasitación")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.setTipoAgenda("Cirugía y Procesos Especializados")
                    viewModel.navigateTo(Screen.SelectFecha)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AB66),
                    contentColor = Color.White
                )
            ) {
                Text("Cirugía y Procesos Especializados")
            }
        }
    }
}