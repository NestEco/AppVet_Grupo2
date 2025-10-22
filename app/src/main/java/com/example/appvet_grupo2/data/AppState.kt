package com.example.appvet_grupo2.data

import androidx.compose.runtime.mutableStateListOf
import com.example.appvet_grupo2.model.Usuario
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.model.HoraAgendada
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppState(private val dataStore: DataStoreManager) {
    val usuarios = mutableStateListOf<Usuario>()
    val mascotas = mutableStateListOf<Mascota>()
    val horasAgendadas = mutableStateListOf<HoraAgendada>()

    var usuarioActual: Usuario? = null

    private val scope = CoroutineScope(Dispatchers.IO)

    // ========== CARGA INICIAL ==========

    // Carga de datos al inicio
    fun cargarDatos() {
        scope.launch {
            // Cargar usuarios
            val users = dataStore.getUsers().first()
            usuarios.clear()
            usuarios.addAll(users)

            // Cargar mascotas
            val pets = dataStore.getMascotas().first()
            mascotas.clear()
            mascotas.addAll(pets)

            // Cargar horas agendadas
            val horas = dataStore.getHorasAgendadas().first()
            horasAgendadas.clear()
            horasAgendadas.addAll(horas)
        }
    }

    // ========== USUARIOS ==========

    // Registrar nuevos usuarios [validando email único]
    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        if (usuarios.any { it.email == email }) return false
        val nuevo = Usuario(nombre = nombre, email = email, password = password)
        usuarios.add(nuevo)
        guardarUsuarios()
        return true
    }

    // Iniciar sesión
    fun login(email: String, password: String): Boolean {
        val user = usuarios.find { it.email == email && it.password == password }
        return if (user != null) {
            usuarioActual = user
            true
        } else false
    }

    // Cerrar sesión
    fun logout() {
        usuarioActual = null
    }

    // Guardar usuarios en DataStore
    private fun guardarUsuarios() {
        scope.launch {
            dataStore.saveUsers(usuarios)
        }
    }

    // ========== MASCOTAS ==========

    // Agregar mascota
    fun agregarMascota(mascota: Mascota) {
        mascotas.add(mascota)
        guardarMascotas()
    }

    // Guardar mascotas en DataStore
    private fun guardarMascotas() {
        scope.launch {
            dataStore.saveMascotas(mascotas)
        }
    }

    // ========== HORAS AGENDADAS ==========

    // Agregar hora agendada
    fun agregarHoraAgendada(hora: HoraAgendada) {
        horasAgendadas.add(hora)
        guardarHorasAgendadas()
    }

    // Guardar horas agendadas en DataStore
    private fun guardarHorasAgendadas() {
        scope.launch {
            dataStore.saveHorasAgendadas(horasAgendadas)
        }
    }
}