package com.school.rollhaven_mobile.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private const val BASE_URL = "http://10.0.2.2:5277/"

    // Création du client HTTP avec un logger pour déboguer les requêtes
    private val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(logging) // Ajout du logger
            .connectTimeout(30, TimeUnit.SECONDS) // Timeout de connexion
            .readTimeout(30, TimeUnit.SECONDS) // Timeout de lecture
            .build()
    }

    // Création de l'instance Retrofit
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Utilisation de Gson pour la sérialisation/désérialisation
            .build()
    }
}