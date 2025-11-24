package com.example.appvet_grupo2.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Tests para modelo Mascota")
class MascotaTest {

    @Test
    @DisplayName("Mascota debe crearse correctamente")
    fun `mascota se crea correctamente`() {
        val mascota = Mascota(
            nombre = "Firulais",
            edad = 3,
            especie = "Perro",
            usuarioId = "user123"
        )

        assertNotNull(mascota.id)
        assertEquals("Firulais", mascota.nombre)
        assertEquals(3, mascota.edad)
        assertEquals("Perro", mascota.especie)
        assertEquals("user123", mascota.usuarioId)
        assertNull(mascota.fotoUri)
    }

    @Test
    @DisplayName("Mascota debe aceptar edad cero")
    fun `mascota acepta edad cero`() {
        val mascota = Mascota(
            nombre = "Cachorro",
            edad = 0,
            especie = "Gato",
            usuarioId = "user123"
        )

        assertEquals(0, mascota.edad)
    }

    @Test
    @DisplayName("Copy debe modificar solo los campos especificados")
    fun `copy modifica solo campos especificados`() {
        val original = Mascota(
            nombre = "Firulais",
            edad = 3,
            especie = "Perro",
            usuarioId = "user123"
        )

        val modificada = original.copy(edad = 4, fotoUri = "uri://foto")

        assertEquals(original.nombre, modificada.nombre)
        assertEquals(4, modificada.edad)
        assertEquals("uri://foto", modificada.fotoUri)
        assertEquals(original.id, modificada.id)
    }
}