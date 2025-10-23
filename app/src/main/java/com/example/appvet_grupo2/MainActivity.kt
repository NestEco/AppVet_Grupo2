package com.example.appvet_grupo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.data.DataStoreManager
import com.example.appvet_grupo2.navigation.AppNavigation
import com.example.appvet_grupo2.ui.theme.AppVet_Grupo2Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val dataStore = DataStoreManager(application)
        val appState = AppState(dataStore)

        setContent {
            AppVet_Grupo2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(appState)
                    }
                }
            }
        }
    }
}