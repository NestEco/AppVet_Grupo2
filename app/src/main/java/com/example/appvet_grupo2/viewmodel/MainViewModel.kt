package com.example.appvet_grupo2.viewmodel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.appvet_grupo2.model.HoraAgendada
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.model.UsuarioErrores
import com.example.appvet_grupo2.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    //VALUES

    // Lista de mascotas
    private val _mascotas = mutableStateListOf<Mascota>()
    val mascotas: List<Mascota> = _mascotas

    // Lista de horas agendadas
    private val _horasAgendadas = mutableStateListOf<HoraAgendada>()
    val horasAgendadas: List<HoraAgendada> = _horasAgendadas


    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    //VARIABLES
    // Datos temporales para crear una hora agendada
    var tipoAgendaTemp by mutableStateOf("")
        private set
    var fechaTemp by mutableStateOf<Long?>(null)
        private set
    var horaTemp by mutableStateOf<Int?>(null)
        private set
    var minutoTemp by mutableStateOf<Int?>(null)
        private set

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    //FUNCIONES

    // Funciones para mascotas
    fun agregarMascota(mascota: Mascota) {
        _mascotas.add(mascota)
    }

    // Funciones para el flujo de agenda
    fun setTipoAgenda(tipo: String) {
        tipoAgendaTemp = tipo
    }

    fun setFecha(fecha: Long) {
        fechaTemp = fecha
    }

    fun setHora(hora: Int, minuto: Int) {
        horaTemp = hora
        minutoTemp = minuto
    }

    fun agregarHoraAgendada() {
        if (fechaTemp != null && horaTemp != null && minutoTemp != null && tipoAgendaTemp.isNotEmpty()) {
            val nuevaHora = HoraAgendada(
                fecha = fechaTemp,
                hora = horaTemp!!,
                minuto = minutoTemp!!,
                tipo = tipoAgendaTemp
            )
            _horasAgendadas.add(nuevaHora)
            limpiarDatosTemporales()
        }
    }

    private fun limpiarDatosTemporales() {
        tipoAgendaTemp = ""
        fechaTemp = null
        horaTemp = null
        minutoTemp = null
    }



}