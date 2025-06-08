package com.example.pam_lastproject

data class Plant(
    val id: Int,
    val plant_name: String,
    val description: String,
    val price: String,
    // Anda bisa menambahkan field lain seperti URL gambar jika ada di API
    // val image_url: String
)