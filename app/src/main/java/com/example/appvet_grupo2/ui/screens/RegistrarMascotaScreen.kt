package com.example.appvet_grupo2.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.viewmodel.MainViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarMascotaScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    appState: AppState
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableIntStateOf(1) }
    var especie by remember { mutableStateOf("Perro") }
    var fotoMascotaUri by remember { mutableStateOf<Uri?>(null) }
    var showEdadPicker by remember { mutableStateOf(false) }
    var showEspeciePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val especiesDisponibles = listOf("Perro", "Gato", "Conejo", "Hámster", "Ave", "Reptil", "Otro")

    // URI temporal para la foto de la cámara
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para seleccionar imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fotoMascotaUri = it
            Toast.makeText(context, "Foto seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para tomar foto con la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempCameraUri?.let {
                fotoMascotaUri = it
                Toast.makeText(context, "Foto capturada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher para permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val photoFile = File(
                context.cacheDir,
                "pet_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
            )
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00AB66),
                    titleContentColor = Color.White,
                ),
                title = { Text("Registrar Mascota") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Foto de la mascota
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { showImagePickerDialog = true },
                contentAlignment = Alignment.Center
            ) {
                if (fotoMascotaUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoMascotaUri),
                        contentDescription = "Foto de mascota",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = "Foto de mascota",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showImagePickerDialog = true }
            ) {
                Text("Cambiar foto")
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la mascota") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "$edad año${if (edad != 1) "s" else ""}",
                onValueChange = { },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showEdadPicker = true }) {
                        Text("▼")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = especie,
                onValueChange = { },
                label = { Text("Especie") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showEspeciePicker = true }) {
                        Text("▼")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        val nuevaMascota = Mascota(
                            nombre = nombre,
                            edad = edad,
                            especie = especie,
                            fotoUri = fotoMascotaUri?.toString()
                        )
                        viewModel.agregarMascota(nuevaMascota)
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank()
            ) {
                Text("Registrar Mascota")
            }
        }

        // Diálogo para seleccionar edad
        if (showEdadPicker) {
            AlertDialog(
                onDismissRequest = { showEdadPicker = false },
                confirmButton = {
                    TextButton(onClick = { showEdadPicker = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEdadPicker = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Seleccionar edad") },
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        NumberPicker(
                            value = edad,
                            onValueChange = { edad = it },
                            range = 0..30
                        )
                    }
                }
            )
        }

        // Diálogo para seleccionar especie
        if (showEspeciePicker) {
            AlertDialog(
                onDismissRequest = { showEspeciePicker = false },
                confirmButton = {
                    TextButton(onClick = { showEspeciePicker = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEspeciePicker = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Seleccionar especie") },
                text = {
                    Column {
                        especiesDisponibles.forEach { especieOpcion ->
                            TextButton(
                                onClick = {
                                    especie = especieOpcion
                                    showEspeciePicker = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(especieOpcion)
                            }
                        }
                    }
                }
            )
        }

        // Diálogo para seleccionar origen de la imagen
        if (showImagePickerDialog) {
            AlertDialog(
                onDismissRequest = { showImagePickerDialog = false },
                title = { Text("Seleccionar foto") },
                text = {
                    Column {
                        TextButton(
                            onClick = {
                                showImagePickerDialog = false
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) -> {
                                        val photoFile = File(
                                            context.cacheDir,
                                            "pet_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
                                        )
                                        val uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.fileprovider",
                                            photoFile
                                        )
                                        tempCameraUri = uri
                                        cameraLauncher.launch(uri)
                                    }
                                    else -> {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Tomar foto")
                            }
                        }

                        TextButton(
                            onClick = {
                                showImagePickerDialog = false
                                galleryLauncher.launch("image/*")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = "Galería")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Seleccionar de galería")
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showImagePickerDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo de éxito
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    navController.navigate("mascotas")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            navController.navigate("mascotas")
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Éxito") },
                text = { Text("Mascota registrada con éxito") }
            )
        }
    }
}