package com.example.pam_lastproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_lastproject.databinding.ActivityAddEditPlantBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditPlantBinding
    private var plantId: Int? = null
    private var originalPlantName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah ini mode edit
        if (intent.hasExtra("PLANT_ID")) {
            plantId = intent.getIntExtra("PLANT_ID", 0)
            originalPlantName = intent.getStringExtra("PLANT_NAME")
            binding.etPlantName.setText(originalPlantName)
            binding.etPlantDescription.setText(intent.getStringExtra("PLANT_DESC"))
            binding.etPlantPrice.setText(intent.getStringExtra("PLANT_PRICE"))
            title = "Edit Tanaman"
        } else {
            title = "Tambah Tanaman"
        }

        binding.btnSave.setOnClickListener {
            savePlant()
        }
    }

    private fun savePlant() {
        val name = binding.etPlantName.text.toString().trim()
        val description = binding.etPlantDescription.text.toString().trim()
        val price = binding.etPlantPrice.text.toString().trim()

        if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val plant = Plant(id = plantId ?: 0, plant_name = name, description = description, price = price)

        val call: Call<Void> = if (plantId != null) {
            // Mode Update
            ApiClient.instance.updatePlant(originalPlantName!!, plant)
        } else {
            // Mode Create
            ApiClient.instance.createPlant(plant)
        }

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddEditPlantActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke MainActivity
                } else {
                    Toast.makeText(this@AddEditPlantActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AddEditPlantActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}