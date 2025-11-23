package com.example.appvet_grupo2.model

data class HoraAgendada(
    val id: String = java.util.UUID.randomUUID().toString(),
    val fecha: Long?,
    val hora: Int,
    val minuto: Int,
    val tipo: String,
    val usuarioId: String,
    val mascotaId: String? = null
)