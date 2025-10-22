package com.example.appvet_grupo2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.appvet_grupo2.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        //El login. Conexiones: Registro, Home
        composable("login") {
            LoginScreen(navController, viewModel)
        }

        //Registro de cuenta. Se pide nombre, correo, contraseña, y verificacion de contraseña. Pide validación
        //Conexiones: Home
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        //La página donde están todas las opciones principales.
        //Conexiones: ReservarHora, Perfil, Mascotas, Agenda, Login
        composable("home") {
            HomeScreen(navController, viewModel)
        }

        //Eliges tres opciones entre "Consulta", "Vacuna y Deparacitacion", y "Cirugia y Procesos Especializados"
        //Conexiones: SelectFecha
        composable("reservarHora") {
            ReservarScreen(navController, viewModel)
        }

        //Seleccionas tu fecha para agendar hora
        //Conexiones: SelectHora
        composable("fecha") {
            FechaScreen(navController, viewModel)
        }

        //Seleccionas la hora para agendarla
        //Conexiones: Home
        composable("hora") {
            HoraScreen(navController, viewModel)
        }

        //El perfil, con foto, nombre, especialidad
        //Conexiones: Home
        composable("perfil") {
            PerfilScreen(navController, viewModel)
        }

        //Se muestran las mascotas del usuario y una opción de agregar
        //Conexiones: Home, RegistrarMascotas
        composable("mascotas") {
            MascotasScreen(navController, viewModel)
        }

        //Permite registrar una mascota, con nombre, edad, y especie
        //Conexiones: Mascotas
        composable("registrarMascota") {
            RegistrarMascotaScreen(navController, viewModel)
        }

        //Permite revisar las horas agendadas
        //Conexiones: Home
        composable("agenda") {
            AgendaScreen(navController, viewModel)
        }
    }
}