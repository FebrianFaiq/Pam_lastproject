package com.example.pam_lastproject

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://uappam.kuncipintu.my.id/"

    // 1. Buat instance OkHttpClient dengan timeout yang lebih lama
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Waktu tunggu untuk terhubung: 30 detik
        .readTimeout(30, TimeUnit.SECONDS)    // Waktu tunggu untuk membaca data: 30 detik
        .writeTimeout(30, TimeUnit.SECONDS)   // Waktu tunggu untuk mengirim data: 30 detik
        .build()

    // 2. Gunakan OkHttpClient yang sudah dikonfigurasi saat membuat Retrofit
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Tambahkan baris ini
            .build()
        retrofit.create(ApiService::class.java)
    }
}