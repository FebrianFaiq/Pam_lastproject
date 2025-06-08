package com.example.pam_lastproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pam_lastproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var plantAdapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadPlants()

        // Fungsi TAMBAH: Membuka AddEditPlantActivity tanpa mengirim data
        binding.btnAddPlant.setOnClickListener {
            val intent = Intent(this, AddEditPlantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        plantAdapter = PlantAdapter(
            mutableListOf(),
            // Aksi #1: EDIT Tanaman
            onEditClick = { plant ->
                val intent = Intent(this, AddEditPlantActivity::class.java).apply {
                    putExtra("PLANT_ID", plant.id)
                    putExtra("PLANT_NAME", plant.plant_name)
                    putExtra("PLANT_DESC", plant.description)
                    putExtra("PLANT_PRICE", plant.price)
                }
                startActivity(intent)
            },
            // Aksi #2: HAPUS Tanaman
            onDeleteClick = { plant ->
                showDeleteConfirmationDialog(plant)
            },
            // Aksi #3: DETAIL Tanaman
            onDetailClick = { plant ->
                val intent = Intent(this, PlantDetailActivity::class.java).apply {
                    putExtra("PLANT_NAME", plant.plant_name)
                    putExtra("PLANT_DESC", plant.description)
                    putExtra("PLANT_PRICE", plant.price)
                }
                startActivity(intent)
            }
        )
        binding.rvPlants.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = plantAdapter
        }
    }

    private fun showDeleteConfirmationDialog(plant: Plant) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Tanaman")
            .setMessage("Apakah Anda yakin ingin menghapus ${plant.plant_name}?")
            .setPositiveButton("Ya") { _, _ -> deletePlant(plant) }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun deletePlant(plant: Plant) {
        ApiClient.instance.deletePlant(plant.plant_name).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "${plant.plant_name} berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadPlants()
                } else {
                    Toast.makeText(this@MainActivity, "Gagal menghapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Memberi tahu user jika ada masalah jaringan
                Toast.makeText(this@MainActivity, "Error Jaringan: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadPlants() {
        ApiClient.instance.getAllPlants().enqueue(object : Callback<PlantResponse> {
            override fun onResponse(call: Call<PlantResponse>, response: Response<PlantResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        plantAdapter.updateData(it.data)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PlantResponse>, t: Throwable) {
                // Memberi tahu user jika ada masalah jaringan
                Toast.makeText(this@MainActivity, "Error Jaringan: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang data setiap kali kembali ke activity ini untuk melihat perubahan
        loadPlants()
    }
}