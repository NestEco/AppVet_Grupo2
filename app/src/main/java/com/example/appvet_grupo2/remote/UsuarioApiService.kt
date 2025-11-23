package com.example.appvet_grupo2.remote

import com.example.appvet_grupo2.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface UsuarioApiService {

    // GET - Obtener todos los usuarios
    @GET("usuarios")
    suspend fun obtenerTodos(): Response<List<Usuario>>

    // GET - Obtener usuario por ID
    @GET("usuarios/{id}")
    suspend fun obtenerPorId(@Path("id") id: String): Response<Usuario>

    // GET - Obtener usuario por email
    @GET("usuarios/email/{email}")
    suspend fun obtenerPorEmail(@Path("email") email: String): Response<Usuario>

    // POST - Registrar nuevo usuario
    @POST("usuarios/registro")
    suspend fun registrar(@Body usuario: Usuario): Response<Usuario>

    // POST - Login
    @POST("usuarios/login")
    suspend fun login(@Body credentials: Map<String, String>): Response<Usuario>

    // PUT - Actualizar usuario
    @PUT("usuarios/{id}")
    suspend fun actualizar(
        @Path("id") id: String,
        @Body usuario: Usuario
    ): Response<Usuario>

    // DELETE - Eliminar usuario
    @DELETE("usuarios/{id}")
    suspend fun eliminar(@Path("id") id: String): Response<Void>

    // GET - Health check
    @GET("usuarios/health")
    suspend fun healthCheck(): Response<Map<String, String>>
}