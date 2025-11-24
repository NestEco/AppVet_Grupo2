package com.example.appvet_grupo2.viewmodel

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

@DisplayName("Tests para UsuarioViewModel")
class UsuarioViewModelTest {

    private lateinit var viewModel: UsuarioViewModel

    @BeforeEach
    fun setup() {
        viewModel = UsuarioViewModel()
    }

    @Test
    @DisplayName("El estado inicial debe estar vacío")
    fun `estado inicial debe estar vacio`() {
        val estado = viewModel.estado.value

        assertEquals("", estado.nombre)
        assertEquals("", estado.correo)
        assertEquals("", estado.clave)
        assertFalse(estado.aceptaTerminos)
    }

    @Test
    @DisplayName("Actualizar nombre debe cambiar el estado")
    fun `actualizar nombre cambia el estado`() {
        viewModel.onNombreChange("Juan")

        assertEquals("Juan", viewModel.estado.value.nombre)
    }

    @Test
    @DisplayName("Actualizar correo debe cambiar el estado")
    fun `actualizar correo cambia el estado`() {
        viewModel.onCorreoChange("juan@example.com")

        assertEquals("juan@example.com", viewModel.estado.value.correo)
    }

    @Test
    @DisplayName("Validación debe fallar con campos vacíos")
    fun `validacion falla con campos vacios`() {
        val resultado = viewModel.validaFormulario()

        assertFalse(resultado)
        assertNotNull(viewModel.estado.value.errores.nombre)
        assertNotNull(viewModel.estado.value.errores.correo)
        assertNotNull(viewModel.estado.value.errores.clave)
    }

    @Test
    @DisplayName("Validación debe fallar con correo inválido")
    fun `validacion falla con correo invalido`() {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("correo-invalido")
        viewModel.onClaveChange("123456")

        val resultado = viewModel.validaFormulario()

        assertFalse(resultado)
        assertNotNull(viewModel.estado.value.errores.correo)
    }

    @Test
    @DisplayName("Validación debe fallar con contraseña corta")
    fun `validacion falla con contraseña corta`() {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@example.com")
        viewModel.onClaveChange("123")

        val resultado = viewModel.validaFormulario()

        assertFalse(resultado)
        assertNotNull(viewModel.estado.value.errores.clave)
    }

    @Test
    @DisplayName("Validación debe pasar con datos correctos")
    fun `validacion pasa con datos correctos`() {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@example.com")
        viewModel.onClaveChange("123456")

        val resultado = viewModel.validaFormulario()

        assertTrue(resultado)
        assertNull(viewModel.estado.value.errores.nombre)
        assertNull(viewModel.estado.value.errores.correo)
        assertNull(viewModel.estado.value.errores.clave)
    }

    @Test
    @DisplayName("Limpiar formulario debe resetear todos los campos")
    fun `limpiar formulario resetea campos`() {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@example.com")
        viewModel.onClaveChange("123456")
        viewModel.onAceptarTerminosChange(true)

        viewModel.limpiarFormulario()

        val estado = viewModel.estado.value
        assertEquals("", estado.nombre)
        assertEquals("", estado.correo)
        assertEquals("", estado.clave)
        assertFalse(estado.aceptaTerminos)
    }
}