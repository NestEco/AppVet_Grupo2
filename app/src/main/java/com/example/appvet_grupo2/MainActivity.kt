package com.example.appvet_grupo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appvet_grupo2.navigation.AppNavigation
import com.example.appvet_grupo2.navigation.NavigationEvent
import com.example.appvet_grupo2.navigation.Screen
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
import com.example.appvet_grupo2.ui.theme.AppVet_Grupo2Theme
import com.example.appvet_grupo2.viewmodel.MainViewModel
import com.example.appvet_grupo2.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppVet_Grupo2Theme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }

                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Agenda.route) {
                            AgendaScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.SelectFecha.route) {
                            FechaScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.SelectHora.route) {
                            HoraScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Login.route) {
                            LoginScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Mascotas.route) {
                            MascotasScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Perfil.route) {
                            PerfilScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(Screen.RegistrarMascotas.route) {
                            RegistrarMascotaScreen(navController, viewModel)
                        }
                        composable(route = Screen.ReservarHora.route) {
                            ReservarScreen(navController = navController, viewModel = viewModel)
                        }

                    }
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
