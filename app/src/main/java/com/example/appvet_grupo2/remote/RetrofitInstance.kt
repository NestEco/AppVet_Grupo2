package com.example.appvet_grupo2.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    // URLs de AWS EC2
    private const val BASE_URL_USUARIOS = "http://34.205.86.178:8080/api/"
    private const val BASE_URL_MASCOTAS = "http://34.205.86.178:8081/api/"
    private const val BASE_URL_HORAS = "http://34.205.86.178:8082/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofitUsuarios: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_USUARIOS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitMascotas: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MASCOTAS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitHoras: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_HORAS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioApi: UsuarioApiService by lazy {
        retrofitUsuarios.create(UsuarioApiService::class.java)
    }

    val mascotaApi: MascotaApiService by lazy {
        retrofitMascotas.create(MascotaApiService::class.java)
    }

    val horaAgendadaApi: HoraAgendadaApiService by lazy {
        retrofitHoras.create(HoraAgendadaApiService::class.java)
    }
}