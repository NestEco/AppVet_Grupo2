package com.example.appvet_grupo2.remote

import com.example.appvet_grupo2.model.Mascota
import retrofit2.Response
import retrofit2.http.*

interface MascotaApiService {

    // GET - Obtener todas las mascotas
    @GET("mascotas")
    suspend fun obtenerTodas(): Response<List<Mascota>>

    // GET - Obtener mascota por ID
    @GET("mascotas/{id}")
    suspend fun obtenerPorId(@Path("id") id: String): Response<Mascota>

    // GET - Obtener mascotas por usuario
    @GET("mascotas/usuario/{usuarioId}")
    suspend fun obtenerPorUsuario(@Path("usuarioId") usuarioId: String): Response<List<Mascota>>

    // GET - Obtener mascotas por especie
    @GET("mascotas/especie/{especie}")
    suspend fun obtenerPorEspecie(@Path("especie") especie: String): Response<List<Mascota>>

    // GET - Contar mascotas por usuario
    @GET("mascotas/usuario/{usuarioId}/count")
    suspend fun contarPorUsuario(@Path("usuarioId") usuarioId: String): Response<Map<String, Long>>

    // POST - Crear nueva mascota
    @POST("mascotas")
    suspend fun crear(@Body mascota: Mascota): Response<Mascota>

    // PUT - Actualizar mascota
    @PUT("mascotas/{id}")
    suspend fun actualizar(
        @Path("id") id: String,
        @Body mascota: Mascota
    ): Response<Mascota>

    // DELETE - Eliminar mascota
    @DELETE("mascotas/{id}")
    suspend fun eliminar(@Path("id") id: String): Response<Void>

    // GET - Health check
    @GET("mascotas/health")
    suspend fun healthCheck(): Response<Map<String, String>>
}