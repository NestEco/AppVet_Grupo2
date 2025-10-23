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

    fun cargarDatos() {
        scope.launch {

            val users = dataStore.getUsers().first()
            usuarios.clear()
            usuarios.addAll(users)


            val pets = dataStore.getMascotas().first()
            mascotas.clear()
            mascotas.addAll(pets)


            val horas = dataStore.getHorasAgendadas().first()
            horasAgendadas.clear()
            horasAgendadas.addAll(horas)
        }
    }

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


    private fun guardarUsuarios() {
        scope.launch {
            dataStore.saveUsers(usuarios)
        }
    }


    fun agregarMascota(mascota: Mascota) {
        mascotas.add(mascota)
        guardarMascotas()
    }


    private fun guardarMascotas() {
        scope.launch {
            dataStore.saveMascotas(mascotas)
        }
    }

    fun agregarHoraAgendada(hora: HoraAgendada) {
        horasAgendadas.add(hora)
        guardarHorasAgendadas()
    }

    private fun guardarHorasAgendadas() {
        scope.launch {
            dataStore.saveHorasAgendadas(horasAgendadas)
        }
    }
}