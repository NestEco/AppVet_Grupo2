package com.example.appvet_grupo2.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Tests para modelo Usuario")
class UsuarioTest {

    @Test
    @DisplayName("Usuario debe crearse con valores por defecto")
    fun `usuario se crea con valores por defecto`() {
        val usuario = Usuario(
            nombre = "Juan",
            email = "juan@example.com",
            password = "123456"
        )

        assertNotNull(usuario.id)
        assertEquals("Juan", usuario.nombre)
        assertEquals("juan@example.com", usuario.email)
        assertEquals("123456", usuario.password)
        assertEquals("Cliente", usuario.rol)
        assertNull(usuario.fotoPerfilUri)
    }

    @Test
    @DisplayName("Usuario debe crearse con todos los parámetros")
    fun `usuario se crea con todos los parametros`() {
        val usuario = Usuario(
            id = "123",
            nombre = "Juan",
            email = "juan@example.com",
            password = "123456",
            rol = "Admin",
            fotoPerfilUri = "uri://foto"
        )

        assertEquals("123", usuario.id)
        assertEquals("Admin", usuario.rol)
        assertEquals("uri://foto", usuario.fotoPerfilUri)
    }

    @Test
    @DisplayName("Copy debe crear nueva instancia con valores modificados")
    fun `copy crea nueva instancia con valores modificados`() {
        val original = Usuario(
            nombre = "Juan",
            email = "juan@example.com",
            password = "123456"
        )

        val copia = original.copy(nombre = "Pedro")

        assertEquals("Pedro", copia.nombre)
        assertEquals(original.email, copia.email)
        assertEquals(original.id, copia.id)
    }
}