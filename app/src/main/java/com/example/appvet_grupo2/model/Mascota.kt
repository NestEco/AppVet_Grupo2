package com.example.appvet_grupo2.model

data class Mascota(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nombre: String,
    val edad: Int,
    val especie: String,
    val fotoUri: String? = null
)