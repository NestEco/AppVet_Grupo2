package com.example.appvet_grupo2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.ui.screens.AgendaScreen
import com.example.appvet_grupo2.ui.screens.FechaScreen
import com.example.appvet_grupo2.ui.screens.HomeScreen
import com.example.appvet_grupo2.ui.screens.HoraScreen
import com.example.appvet_grupo2.ui.screens.LoginScreen
import com.example.appvet_grupo2.ui.screens.MascotasScreen
import com.example.appvet_grupo2.ui.screens.PerfilScreen
import com.example.appvet_grupo2.ui.screens.RegistrarMascotaScreen
import com.example.appvet_grupo2.ui.screens.RegistroScreen
import com.example.appvet_grupo2.ui.screens.ReservarScreen
import com.example.appvet_grupo2.viewmodel.MainViewModel
import com.example.appvet_grupo2.viewmodel.MainViewModelFactory
import com.example.appvet_grupo2.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(appState: AppState) {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // Crear MainViewModel con AppState usando Factory
    val mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(appState)
    )

    // Cargar datos al iniciar la app
    LaunchedEffect(Unit) {
        appState.cargarDatos()
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // El login. Conexiones: Registro, Home
        composable("login") {
            LoginScreen(navController, appState)
        }

        // Registro de cuenta. Se pide nombre, correo, contraseña, y verificación de contraseña
        // Conexiones: Home, Login
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel, appState)
        }

        // La página donde están todas las opciones principales
        // Conexiones: ReservarHora, Perfil, Mascotas, Agenda, Login
        composable("home") {
            HomeScreen(navController, mainViewModel, appState)
        }

        // Eliges tres opciones entre "Consulta", "Vacuna y Desparasitación", y "Cirugía y Procesos Especializados"
        // Conexiones: SelectFecha
        composable("reservarHora") {
            ReservarScreen(navController, mainViewModel, appState)
        }

        // Seleccionas tu fecha para agendar hora
        // Conexiones: SelectHora
        composable("fecha") {
            FechaScreen(navController, mainViewModel, appState)
        }

        // Seleccionas la hora para agendarla
        // Conexiones: Agenda
        composable("hora") {
            HoraScreen(navController, mainViewModel, appState)
        }

        // El perfil, con foto, nombre, especialidad
        // Conexiones: Home, Mascotas, Agenda, Login
        composable("perfil") {
            PerfilScreen(navController, mainViewModel, appState)
        }

        // Se muestran las mascotas del usuario y una opción de agregar
        // Conexiones: Home, Perfil, Agenda, RegistrarMascotas, Login
        composable("mascotas") {
            MascotasScreen(navController, mainViewModel, appState)
        }

        // Permite registrar una mascota, con nombre, edad, y especie
        // Conexiones: Mascotas
        composable("registrarMascota") {
            RegistrarMascotaScreen(navController, mainViewModel, appState)
        }

        // Permite revisar las horas agendadas
        // Conexiones: Home, Perfil, Mascotas, ReservarHora, Login
        composable("agenda") {
            AgendaScreen(navController, mainViewModel, appState)
        }
    }
}