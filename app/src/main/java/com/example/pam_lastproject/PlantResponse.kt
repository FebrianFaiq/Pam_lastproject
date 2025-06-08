package com.example.pam_lastproject

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("message")
    val message: String,

    // Karena "data" bisa jadi satu objek atau list, kita perlu penyesuaian
    // Namun, dari contoh Anda, sepertinya /plant/all mengembalikan SATU objek
    // di dalam "data", bukan list. Ini aneh untuk endpoint "all".
    // Mari kita asumsikan seharusnya itu adalah list. Jika tidak, API-nya
    // perlu diperbaiki. Untuk saat ini, kita akan coba tangani sebagai list.
    @SerializedName("data")
    val data: List<Plant>
)