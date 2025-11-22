package com.example.appvet_grupo2.remote

import com.example.appvet_grupo2.model.HoraAgendada
import retrofit2.Response
import retrofit2.http.*

interface HoraAgendadaApiService {

    // GET - Obtener todas las horas agendadas
    @GET("horas-agendadas")
    suspend fun obtenerTodas(): Response<List<HoraAgendada>>

    // GET - Obtener hora agendada por ID
    @GET("horas-agendadas/{id}")
    suspend fun obtenerPorId(@Path("id") id: String): Response<HoraAgendada>

    // GET - Obtener horas agendadas por usuario
    @GET("horas-agendadas/usuario/{usuarioId}")
    suspend fun obtenerPorUsuario(@Path("usuarioId") usuarioId: String): Response<List<HoraAgendada>>

    // GET - Obtener horas agendadas por mascota
    @GET("horas-agendadas/mascota/{mascotaId}")
    suspend fun obtenerPorMascota(@Path("mascotaId") mascotaId: String): Response<List<HoraAgendada>>

    // GET - Obtener horas agendadas por estado
    @GET("horas-agendadas/estado/{estado}")
    suspend fun obtenerPorEstado(@Path("estado") estado: String): Response<List<HoraAgendada>>

    // GET - Obtener horas agendadas por tipo
    @GET("horas-agendadas/tipo/{tipo}")
    suspend fun obtenerPorTipo(@Path("tipo") tipo: String): Response<List<HoraAgendada>>

    // POST - Crear nueva hora agendada
    @POST("horas-agendadas")
    suspend fun crear(@Body horaAgendada: HoraAgendada): Response<HoraAgendada>

    // PUT - Actualizar hora agendada
    @PUT("horas-agendadas/{id}")
    suspend fun actualizar(
        @Path("id") id: String,
        @Body horaAgendada: HoraAgendada
    ): Response<HoraAgendada>

    // PATCH - Cambiar estado
    @PATCH("horas-agendadas/{id}/estado")
    suspend fun cambiarEstado(
        @Path("id") id: String,
        @Body estado: Map<String, String>
    ): Response<HoraAgendada>

    // DELETE - Eliminar hora agendada
    @DELETE("horas-agendadas/{id}")
    suspend fun eliminar(@Path("id") id: String): Response<Void>

    // GET - Health check
    @GET("horas-agendadas/health")
    suspend fun healthCheck(): Response<Map<String, String>>
}