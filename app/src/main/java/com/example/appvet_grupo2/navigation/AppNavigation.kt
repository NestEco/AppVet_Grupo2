package com.example.appvet_grupo2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.ui.screens.AgendaScreen
import com.example.appvet_grupo2.ui.screens.EditarMascotaScreen
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
import com.example.appvet_grupo2.ui.screens.SeleccionarMascotaScreen

@Composable
fun AppNavigation(appState: AppState) {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()


    val mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(appState)
    )


    LaunchedEffect(Unit) {
        appState.cargarDatos()
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(navController, appState)
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel, appState)
        }


        composable("home") {
            HomeScreen(navController, mainViewModel, appState)
        }


        composable("reservarHora") {
            ReservarScreen(navController, mainViewModel, appState)
        }


        composable("fecha") {
            FechaScreen(navController, mainViewModel, appState)
        }

        composable("seleccionarMascota") {
            SeleccionarMascotaScreen(navController, mainViewModel, appState)
        }


        composable("hora") {
            HoraScreen(navController, mainViewModel, appState)
        }


        composable("perfil") {
            PerfilScreen(navController, mainViewModel, appState)
        }


        composable("mascotas") {
            MascotasScreen(navController, mainViewModel, appState)
        }


        composable("registrarMascota") {
            RegistrarMascotaScreen(navController, mainViewModel, appState)
        }


        composable("agenda") {
            AgendaScreen(navController, mainViewModel, appState)
        }

        composable(
            route = "editarMascota/{mascotaId}",
            arguments = listOf(navArgument("mascotaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mascotaId = backStackEntry.arguments?.getString("mascotaId") ?: ""
            EditarMascotaScreen(navController, mascotaId, mainViewModel, appState)
        }

    }
}