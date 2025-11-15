package com.example.appvet_grupo2.model

data class Usuario(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String = "Cliente",
    val fotoPerfilUri: String? = null
)