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

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
