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
            // Cargar usuarios desde DataStore (local)
            val users = dataStore.getUsers().first()
            usuarios.clear()
            usuarios.addAll(users)

            // Cargar mascotas desde la API
            cargarMascotasDesdeApi()

            // Cargar horas agendadas desde la API
            cargarHorasDesdeApi()
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
                // Fallback: cargar desde DataStore si falla la API
                val mascotasLocal = dataStore.getMascotas().first()
                withContext(Dispatchers.Main) {
                    mascotas.clear()
                    mascotas.addAll(mascotasLocal)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red al cargar mascotas: ${e.message}")
            // Fallback: cargar desde DataStore
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
                // Fallback
                val horasLocal = dataStore.getHorasAgendadas().first()
                withContext(Dispatchers.Main) {
                    horasAgendadas.clear()
                    horasAgendadas.addAll(horasLocal)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red al cargar horas: ${e.message}")
            // Fallback
            val horasLocal = dataStore.getHorasAgendadas().first()
            withContext(Dispatchers.Main) {
                horasAgendadas.clear()
                horasAgendadas.addAll(horasLocal)
            }
        }
    }

    // ==========================================
    // USUARIOS (DataStore - Local)
    // ==========================================

    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        if (usuarios.any { it.email == email }) return false
        val nuevo = Usuario(nombre = nombre, email = email, password = password)
        usuarios.add(nuevo)
        guardarUsuarios()
        return true
    }

    fun login(email: String, password: String): Boolean {
        val user = usuarios.find { it.email == email && it.password == password }
        return if (user != null) {
            usuarioActual = user
            true
        } else false
    }

    fun logout() {
        usuarioActual = null
    }

    fun actualizarFotoPerfil(fotoUri: String) {
        usuarioActual?.let { usuario ->
            val usuarioActualizado = usuario.copy(fotoPerfilUri = fotoUri)
            val index = usuarios.indexOfFirst { it.id == usuario.id }
            if (index != -1) {
                usuarios[index] = usuarioActualizado
                usuarioActual = usuarioActualizado
                guardarUsuarios()
            }
        }
    }

    fun actualizarNombre(nuevoNombre: String) {
        usuarioActual?.let { usuario ->
            val usuarioActualizado = usuario.copy(nombre = nuevoNombre)
            val index = usuarios.indexOfFirst { it.id == usuario.id }
            if (index != -1) {
                usuarios[index] = usuarioActualizado
                usuarioActual = usuarioActualizado
                guardarUsuarios()
            }
        }
    }

    private fun guardarUsuarios() {
        scope.launch {
            dataStore.saveUsers(usuarios)
        }
    }

    // ==========================================
    // MASCOTAS (Retrofit - API)
    // ==========================================

    fun agregarMascota(mascota: Mascota) {
        scope.launch {
            try {
                val response = RetrofitInstance.mascotaApi.crear(mascota)
                if (response.isSuccessful) {
                    response.body()?.let { nuevaMascota ->
                        withContext(Dispatchers.Main) {
                            mascotas.add(nuevaMascota)
                        }
                        // También guardar en DataStore como backup
                        dataStore.saveMascotas(mascotas)
                        Log.d(TAG, "Mascota creada en API: ${nuevaMascota.nombre}")
                    }
                } else {
                    Log.e(TAG, "Error al crear mascota: ${response.code()}")
                    // Fallback: guardar solo local
                    withContext(Dispatchers.Main) {
                        mascotas.add(mascota)
                    }
                    dataStore.saveMascotas(mascotas)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al crear mascota: ${e.message}")
                // Fallback: guardar solo local
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
                    // Fallback
                    withContext(Dispatchers.Main) {
                        horasAgendadas.add(hora)
                    }
                    dataStore.saveHorasAgendadas(horasAgendadas)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error de red al crear hora: ${e.message}")
                // Fallback
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