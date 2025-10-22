package com.example.appvet_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appvet_grupo2.model.HoraAgendada
import com.example.appvet_grupo2.navigation.Screen
import com.example.appvet_grupo2.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        //Drawer, barra lateral
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Home)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Perfil)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Mascotas") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Mascotas)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Horas Agendadas") },
                    selected = true,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Login)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Horas Agendadas")},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.navigateTo(Screen.ReservarHora)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agendar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agendar")
                    }
                }
            }
        ) { innerPadding ->
            if (viewModel.horasAgendadas.isEmpty()) {
                // Mensaje cuando no hay horas agendadas
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay horas agendadas",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Presiona el botón Agendar para crear una",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }
            } else {
                // Lista de horas agendadas
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.horasAgendadas) { horaAgendada ->
                        HoraAgendadaCard(horaAgendada = horaAgendada)
                    }
                }
            }
        }
    }
}

@Composable
fun HoraAgendadaCard(horaAgendada: HoraAgendada) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de calendario circular
            Box(
                modifier = Modifier
                    .size(68.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Hora agendada",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la hora agendada
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Tipo de agenda
                Text(
                    text = horaAgendada.tipo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Fecha
                if (horaAgendada.fecha != null) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateString = formatter.format(Date(horaAgendada.fecha))
                    Text(
                        text = "Fecha: $dateString",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Hora
                val hourFormatted = String.format("%02d", horaAgendada.hora)
                val minuteFormatted = String.format("%02d", horaAgendada.minuto)
                Text(
                    text = "Hora: $hourFormatted:$minuteFormatted",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}