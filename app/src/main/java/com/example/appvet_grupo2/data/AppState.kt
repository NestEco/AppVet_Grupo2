package com.example.appvet_grupo2.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.appvet_grupo2.model.Usuario
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.model.HoraAgendada
import com.example.appvet_grupo2.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppState(private val dataStore: DataStoreManager) {
    private val TAG = "AppState"

    val usuarios = mutableStateListOf<Usuario>()
    val mascotas = mutableStateListOf<Mascota>()
    val horasAgendadas = mutableStateListOf<HoraAgendada>()

    var usuarioActual: Usuario? = null

    private val scope = CoroutineScope(Dispatchers.IO)

    // ==========================================
    // CARGA DE DATOS
    // ==========================================

    fun cargarDatos() {
        scope.launch {
            // Cargar usuarios desde la API
            cargarUsuariosDesdeApi()

            // Cargar mascotas desde la API
            cargarMascotasDesdeApi()

            // Cargar horas agendadas desde la API
            cargarHorasDesdeApi()
        }
    }

    private suspend fun cargarUsuariosDesdeApi() {
        try {
            val response = RetrofitInstance.usuarioApi.obtenerTodos()
            if (response.isSuccessful) {
                response.body()?.let { usuariosApi ->
                    withContext(Dispatchers.Main) {
                        usuarios.clear()
                        usuarios.addAll(usuariosApi)
                    }
                    // También guardar en DataStore como backup
                    dataStore.saveUsers(usuariosApi)
                    Log.d(TAG, "Usuarios cargados desde API: ${usuariosApi.size}")
                }
            } else {
                Log.e(TAG, "Error al cargar usuarios: ${response.code()}")
                // Fallback: cargar desde DataStore
                val usuariosLocal = dataStore.getUsers().first()
                withContext(Dispatchers.Main) {
                    usuarios.clear()
                    usuarios.addAll(usuariosLocal)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red al cargar usuarios: ${e.message}")
            // Fallback: cargar desde DataStore
            val usuariosLocal = dataStore.getUsers().first()
            withContext(Dispatchers.Main) {
                usuarios.clear()
                usuarios.addAll(usuariosLocal)
            }
        }
    }

    private suspend fun cargarMascotasDesdeApi() {
        try {
            val response = RetrofitInstance.mascotaApi.obtenerTodas()
            if (response.isSuccessful) {
                response.body()?.let { mascotasApi ->
                    withContext(Dispatchers.Main) {
                        mascotas.clear()
                        mascotas.addAll(mascotasApi)
                    }
                    Log.d(TAG, "Mascotas cargadas desde API: ${mascotasApi.size}")
                }
            } else {
                Log.e(TAG, "Error al cargar mascotas: ${response.code()}")
                val mascotasLocal = dataStore.getMascotas().first()
                withContext(Dispatchers.Main) {
                    mascotas.clear()
                    mascotas.addAll(mascotasLocal)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red al cargar mascotas: ${e.message}")
            val mascotasLocal = dataStore.getMascotas().first()
            withContext(Dispatchers.Main) {
                mascotas.clear()
                mascotas.addAll(mascotasLocal)
            }
        }
    }

    private suspend fun cargarHorasDesdeApi() {
        try {
            val response = RetrofitInstance.horaAgendadaApi.obtenerTodas()
            if (response.isSuccessful) {
                response.body()?.let { horasApi ->
                    withContext(Dispatchers.Main) {
                        horasAgendadas.clear()
                        horasAgendadas.addAll(horasApi)
                    }
                    Log.d(TAG, "Horas cargadas desde API: ${horasApi.size}")
                }
            } else {
                Log.e(TAG, "Error al cargar horas: ${response.code()}")
                val horasLocal = dataStore.getHorasAgendadas().first()
                withContext(Dispatchers.Main) {
                    horasAgendadas.clear()
                    horasAgendadas.addAll(horasLocal)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red al cargar horas: ${e.message}")
            val horasLocal = dataStore.getHorasAgendadas().first()
            withContext(Dispatchers.Main) {
                horasAgendadas.clear()
                horasAgendadas.addAll(horasLocal)
            }
        }
    }

    // ==========================================
    // USUARIOS (Retrofit - API)
    // ==========================================

    fun registrarUsuario(nombre: String, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        scope.launch {
            try {
                // Verificar si el email ya existe
                if (usuarios.any { it.email == email }) {
                    withContext(Dispatchers.Main) {
                        callback(false, "El email ya está registrado")
                    }
                    return@launch
                }

                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    email = email,
                    password = password,
                    rol = "Cliente"
                )

                val response = RetrofitInstance.usuarioApi.registrar(nuevoUsuario)
                if (response.isSuccessful) {
                    response.body()?.let { usuarioCreado ->
                        withContext(Dispatchers.Main) {
                            usuarios.add(usuarioCreado)
                        }
                        // También guardar en DataStore como backup
                        dataStore.saveUsers(usuarios)
                        Log.d(TAG, "Usuario registrado en API: ${usuarioCreado.email}")

                        withContext(Dispatchers.Main) {
                            callback(true, null)
                        }
                    }
                } else {
                    Log.e(TAG, "Error al registrar usuario: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        callback(false, "Error al registrar usuario")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al registrar usuario: ${e.message}")
                withContext(Dispatchers.Main) {
                    callback(false, "Error de conexión: ${e.message}")
                }
            }
        }
    }

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        scope.launch {
            try {
                val credentials = mapOf(
                    "email" to email,
                    "password" to password
                )

                val response = RetrofitInstance.usuarioApi.login(credentials)
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        withContext(Dispatchers.Main) {
                            usuarioActual = usuario
                            Log.d(TAG, "Login exitoso: ${usuario.nombre} - ID: ${usuario.id}")
                            callback(true)
                        }
                    } ?: run {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }
                } else {
                    Log.e(TAG, "Login fallido: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        callback(false)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red en login: ${e.message}")
                // Fallback: intentar login local
                val user = usuarios.find { it.email == email && it.password == password }
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        usuarioActual = user
                        Log.d(TAG, "Login local exitoso: ${user.nombre}")
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }
        }
    }

    fun logout() {
        usuarioActual = null
    }

    fun actualizarFotoPerfil(fotoUri: String) {
        usuarioActual?.let { usuario ->
            scope.launch {
                try {
                    val usuarioActualizado = usuario.copy(fotoPerfilUri = fotoUri)
                    val response = RetrofitInstance.usuarioApi.actualizar(usuario.id, usuarioActualizado)

                    if (response.isSuccessful) {
                        val index = usuarios.indexOfFirst { it.id == usuario.id }
                        if (index != -1) {
                            withContext(Dispatchers.Main) {
                                usuarios[index] = usuarioActualizado
                                usuarioActual = usuarioActualizado
                            }
                            dataStore.saveUsers(usuarios)
                            Log.d(TAG, "Foto de perfil actualizada en API")
                        }
                    } else {
                        Log.e(TAG, "Error al actualizar foto de perfil: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error de red al actualizar foto: ${e.message}")
                }
            }
        }
    }

    fun actualizarNombre(nuevoNombre: String) {
        usuarioActual?.let { usuario ->
            scope.launch {
                try {
                    val usuarioActualizado = usuario.copy(nombre = nuevoNombre)
                    val response = RetrofitInstance.usuarioApi.actualizar(usuario.id, usuarioActualizado)

                    if (response.isSuccessful) {
                        val index = usuarios.indexOfFirst { it.id == usuario.id }
                        if (index != -1) {
                            withContext(Dispatchers.Main) {
                                usuarios[index] = usuarioActualizado
                                usuarioActual = usuarioActualizado
                            }
                            dataStore.saveUsers(usuarios)
                            Log.d(TAG, "Nombre actualizado en API")
                        }
                    } else {
                        Log.e(TAG, "Error al actualizar nombre: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error de red al actualizar nombre: ${e.message}")
                }
            }
        }
    }

    // ==========================================
    // MASCOTAS (Retrofit - API)
    // ==========================================

    fun agregarMascota(mascota: Mascota) {
        scope.launch {
            try {
                Log.d(TAG, "Creando mascota - usuarioId: ${mascota.usuarioId}")

                val response = RetrofitInstance.mascotaApi.crear(mascota)
                if (response.isSuccessful) {
                    response.body()?.let { nuevaMascota ->
                        withContext(Dispatchers.Main) {
                            mascotas.add(nuevaMascota)
                        }
                        dataStore.saveMascotas(mascotas)
                        Log.d(TAG, "Mascota creada en API: ${nuevaMascota.nombre}")
                    }
                } else {
                    Log.e(TAG, "Error al crear mascota: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        mascotas.add(mascota)
                    }
                    dataStore.saveMascotas(mascotas)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al crear mascota: ${e.message}")
                withContext(Dispatchers.Main) {
                    mascotas.add(mascota)
                }
                dataStore.saveMascotas(mascotas)
            }
        }
    }

    fun actualizarMascota(mascota: Mascota) {
        scope.launch {
            try {
                val response = RetrofitInstance.mascotaApi.actualizar(mascota.id, mascota)
                if (response.isSuccessful) {
                    val index = mascotas.indexOfFirst { it.id == mascota.id }
                    if (index != -1) {
                        withContext(Dispatchers.Main) {
                            mascotas[index] = mascota
                        }
                        dataStore.saveMascotas(mascotas)
                        Log.d(TAG, "Mascota actualizada en API: ${mascota.nombre}")
                    }
                } else {
                    Log.e(TAG, "Error al actualizar mascota: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al actualizar mascota: ${e.message}")
            }
        }
    }

    fun eliminarMascota(mascotaId: String) {
        scope.launch {
            try {
                val response = RetrofitInstance.mascotaApi.eliminar(mascotaId)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        mascotas.removeAll { it.id == mascotaId }
                    }
                    dataStore.saveMascotas(mascotas)
                    Log.d(TAG, "Mascota eliminada de API: $mascotaId")
                } else {
                    Log.e(TAG, "Error al eliminar mascota: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al eliminar mascota: ${e.message}")
            }
        }
    }

    // ==========================================
    // HORAS AGENDADAS (Retrofit - API)
    // ==========================================

    fun agregarHoraAgendada(hora: HoraAgendada) {
        scope.launch {
            try {
                Log.d(TAG, "=== CREANDO HORA AGENDADA ===")
                Log.d(TAG, "Usuario ID: ${hora.usuarioId}")
                Log.d(TAG, "Mascota ID: ${hora.mascotaId}")
                Log.d(TAG, "Tipo: ${hora.tipo}")
                Log.d(TAG, "Fecha: ${hora.fecha}")
                Log.d(TAG, "Hora: ${hora.hora}:${hora.minuto}")

                val response = RetrofitInstance.horaAgendadaApi.crear(hora)
                if (response.isSuccessful) {
                    response.body()?.let { nuevaHora ->
                        withContext(Dispatchers.Main) {
                            horasAgendadas.add(nuevaHora)
                        }
                        dataStore.saveHorasAgendadas(horasAgendadas)
                        Log.d(TAG, "Hora agendada creada en API: ${nuevaHora.tipo}")
                    }
                } else {
                    Log.e(TAG, "Error al crear hora: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        horasAgendadas.add(hora)
                    }
                    dataStore.saveHorasAgendadas(horasAgendadas)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al crear hora: ${e.message}")
                withContext(Dispatchers.Main) {
                    horasAgendadas.add(hora)
                }
                dataStore.saveHorasAgendadas(horasAgendadas)
            }
        }
    }

    fun eliminarHoraAgendada(horaId: String) {
        scope.launch {
            try {
                val response = RetrofitInstance.horaAgendadaApi.eliminar(horaId)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        horasAgendadas.removeAll { it.id == horaId }
                    }
                    dataStore.saveHorasAgendadas(horasAgendadas)
                    Log.d(TAG, "Hora eliminada de API: $horaId")
                } else {
                    Log.e(TAG, "Error al eliminar hora: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al eliminar hora: ${e.message}")
            }
        }
    }
}