package com.example.appvet_grupo2.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    // IMPORTANTE: Cambia esta IP según tu configuración
    // - Para emulador Android: usa "10.0.2.2"
    // - Para dispositivo físico: usa la IP de tu PC en la red local (ej: "192.168.1.100")
    private const val BASE_URL_MASCOTAS = "http://192.168.1.18:8081/api/"
    private const val BASE_URL_HORAS = "http://192.168.1.18:8082/api/"

    // Logging interceptor para ver las peticiones en Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cliente HTTP con timeouts y logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Retrofit para Microservicio de Mascotas
    private val retrofitMascotas: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MASCOTAS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit para Microservicio de Horas Agendadas
    private val retrofitHoras: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_HORAS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API de Mascotas
    val mascotaApi: MascotaApiService by lazy {
        retrofitMascotas.create(MascotaApiService::class.java)
    }

    // API de Horas Agendadas
    val horaAgendadaApi: HoraAgendadaApiService by lazy {
        retrofitHoras.create(HoraAgendadaApiService::class.java)
    }
}