package com.example.appvet_grupo2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appvet_grupo2.data.AppState
import com.example.appvet_grupo2.model.HoraAgendada
import com.example.appvet_grupo2.model.Mascota

class MainViewModel(val appState: AppState) : ViewModel() {

    // Acceso a los datos desde AppState
    val mascotas get() = appState.mascotas
    val horasAgendadas get() = appState.horasAgendadas
    val usuarioActual get() = appState.usuarioActual

    // Datos temporales para crear una hora agendada
    var tipoAgendaTemp by mutableStateOf("")
        private set
    var fechaTemp by mutableStateOf<Long?>(null)
        private set
    var mascotaIdTemp by mutableStateOf<String?>(null)  // NUEVO CAMPO
        private set
    var horaTemp by mutableStateOf<Int?>(null)
        private set
    var minutoTemp by mutableStateOf<Int?>(null)
        private set

    // ========== FUNCIONES MASCOTAS ==========

    fun agregarMascota(mascota: Mascota) {
        appState.agregarMascota(mascota)
    }

    // ========== FUNCIONES AGENDA ==========

    fun setTipoAgenda(tipo: String) {
        tipoAgendaTemp = tipo
    }

    fun setFecha(fecha: Long) {
        fechaTemp = fecha
    }

    fun setMascota(mascotaId: String) {
        mascotaIdTemp = mascotaId
    }

    fun setHora(hora: Int, minuto: Int) {
        horaTemp = hora
        minutoTemp = minuto
    }

    fun agregarHoraAgendada() {
        val usuarioId = appState.usuarioActual?.id

        if (fechaTemp != null && horaTemp != null && minutoTemp != null && tipoAgendaTemp.isNotEmpty() && mascotaIdTemp != null && usuarioId != null) {
            val nuevaHora = HoraAgendada(
                fecha = fechaTemp,
                hora = horaTemp!!,
                minuto = minutoTemp!!,
                tipo = tipoAgendaTemp,
                usuarioId = usuarioId,
                mascotaId = mascotaIdTemp
            )
            appState.agregarHoraAgendada(nuevaHora)
            limpiarDatosTemporales()
        }
    }

    private fun limpiarDatosTemporales() {
        tipoAgendaTemp = ""
        fechaTemp = null
        mascotaIdTemp = null
        horaTemp = null
        minutoTemp = null
    }
}