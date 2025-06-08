package com.example.pam_lastproject

import com.google.gson.annotations.SerializedName

// Pastikan 'data' adalah List<Plant> untuk menerima semua data dari API
data class PlantResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Plant>
)