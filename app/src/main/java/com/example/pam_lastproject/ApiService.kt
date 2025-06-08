package com.example.pam_lastproject

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("plant/all")
    fun getAllPlants(): Call<PlantResponse>

    @GET("plant/{plant_name}")
    fun getPlantByName(@Path("plant_name") plantName: String): Call<Plant>

    @POST("plant/new")
    fun createPlant(@Body plant: Plant): Call<Void>

    @PUT("plant/{plant_name}")
    fun updatePlant(@Path("plant_name") plantName: String, @Body plant: Plant): Call<Void>

    @DELETE("plant/{plant_name}")
    fun deletePlant(@Path("plant_name") plantName: String): Call<Void>
}