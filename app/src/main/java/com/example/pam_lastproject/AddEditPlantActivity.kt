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
    // Simpan nama asli untuk klausa WHERE di API saat update
    private var originalPlantName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah intent membawa data untuk mode edit
        if (intent.hasExtra("PLANT_ID")) {
            plantId = intent.getIntExtra("PLANT_ID", 0)
            originalPlantName = intent.getStringExtra("PLANT_NAME")

            binding.etPlantName.setText(originalPlantName)
            binding.etPlantDescription.setText(intent.getStringExtra("PLANT_DESC"))
            binding.etPlantPrice.setText(intent.getStringExtra("PLANT_PRICE"))

            // Ubah judul halaman
            supportActionBar?.title = "Edit Tanaman"
            binding.btnSave.text = "Update"
        } else {
            // Mode Tambah
            supportActionBar?.title = "Tambah Tanaman"
            binding.btnSave.text = "Simpan"
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
            ApiClient.instance.updatePlant(originalPlantName!!, plant)
        } else {
            ApiClient.instance.createPlant(plant)
        }

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddEditPlantActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddEditPlantActivity, "Gagal menyimpan data: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Memberi tahu user jika ada masalah jaringan
                Toast.makeText(this@AddEditPlantActivity, "Error Jaringan: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}