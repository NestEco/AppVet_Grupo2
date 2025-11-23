package com.example.appvet_grupo2.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarMascotaScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    appState: AppState
) {
    // Filtrar mascotas del usuario actual
    val mascotasDelUsuario = remember(appState.mascotas, appState.usuarioActual) {
        appState.mascotas.filter { it.usuarioId == appState.usuarioActual?.id }
    }

    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White
                ),
                title = { Text("Seleccionar Mascota") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Información del tipo de agenda y fecha
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tipo: ${viewModel.tipoAgendaTemp}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00AB66)
                    )

                    if (viewModel.fechaTemp != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                        val dateString = formatter.format(java.util.Date(viewModel.fechaTemp!!))
                        Text(
                            text = "Fecha: $dateString",
                            fontSize = 16.sp,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
            }

            // Título
            Text(
                text = "Seleccione la mascota para la cita",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color(0xFF00AB66)
            )

            if (mascotasDelUsuario.isEmpty()) {
                // No hay mascotas registradas
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Pets,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No tienes mascotas registradas",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Registra una mascota antes de agendar una cita",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { navController.navigate("registrarMascota") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00AB66),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Registrar Mascota")
                        }
                    }
                }
            } else {
                // Lista de mascotas
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mascotasDelUsuario) { mascota ->
                        MascotaSeleccionableCard(
                            mascota = mascota,
                            isSelected = mascotaSeleccionada?.id == mascota.id,
                            onClick = { mascotaSeleccionada = mascota }
                        )
                    }

                    // Espacio al final
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }

                // Botón continuar (fixed al bottom)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            mascotaSeleccionada?.let { mascota ->
                                viewModel.setMascota(mascota.id)
                                navController.navigate("fecha")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00AB66),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = mascotaSeleccionada != null
                    ) {
                        Text(
                            text = "Continuar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MascotaSeleccionableCard(
    mascota: Mascota,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE8F5E9) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto de la mascota
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFF00AB66).copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.primaryContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (mascota.fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(Uri.parse(mascota.fotoUri)),
                        contentDescription = "Foto de ${mascota.nombre}",
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = "Foto de ${mascota.nombre}",
                        modifier = Modifier.size(40.dp),
                        tint = if (isSelected) Color(0xFF00AB66)
                        else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la mascota
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = mascota.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFF00AB66)
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${mascota.edad} año${if (mascota.edad != 1) "s" else ""}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = mascota.especie,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Indicador de selección
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Seleccionada",
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFF00AB66)
                )
            }
        }
    }
}