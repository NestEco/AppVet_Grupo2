package com.example.appvet_grupo2.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Tests para modelo HoraAgendada")
class HoraAgendadaTest {

    @Test
    @DisplayName("HoraAgendada debe crearse correctamente")
    fun `hora agendada se crea correctamente`() {
        val fecha = System.currentTimeMillis()
        val hora = HoraAgendada(
            fecha = fecha,
            hora = 14,
            minuto = 30,
            tipo = "Consulta",
            usuarioId = "user123",
            mascotaId = "mascota456"
        )

        assertNotNull(hora.id)
        assertEquals(fecha, hora.fecha)
        assertEquals(14, hora.hora)
        assertEquals(30, hora.minuto)
        assertEquals("Consulta", hora.tipo)
        assertEquals("user123", hora.usuarioId)
        assertEquals("mascota456", hora.mascotaId)
    }

    @Test
    @DisplayName("HoraAgendada debe aceptar mascotaId nulo")
    fun `hora agendada acepta mascotaId nulo`() {
        val hora = HoraAgendada(
            fecha = System.currentTimeMillis(),
            hora = 10,
            minuto = 0,
            tipo = "Vacunación",
            usuarioId = "user123",
            mascotaId = null
        )

        assertNull(hora.mascotaId)
    }

    @Test
    @DisplayName("HoraAgendada debe validar horas válidas")
    fun `hora agendada valida horas validas`() {
        val hora = HoraAgendada(
            fecha = System.currentTimeMillis(),
            hora = 23,
            minuto = 59,
            tipo = "Cirugía",
            usuarioId = "user123"
        )

        assertTrue(hora.hora in 0..23)
        assertTrue(hora.minuto in 0..59)
    }
}