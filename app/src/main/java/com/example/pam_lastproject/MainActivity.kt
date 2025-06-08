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

        binding.fabAddPlant.setOnClickListener {
            // Arahkan ke AddEditPlantActivity untuk menambah data baru
            // Kita akan buat activity ini selanjutnya
            val intent = Intent(this, AddEditPlantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        plantAdapter = PlantAdapter(
            mutableListOf(),
            onItemClick = { plant ->
                // Arahkan ke AddEditPlantActivity dengan data tanaman untuk diedit
                val intent = Intent(this, AddEditPlantActivity::class.java).apply {
                    putExtra("PLANT_ID", plant.id)
                    putExtra("PLANT_NAME", plant.plant_name)
                    putExtra("PLANT_DESC", plant.description)
                    putExtra("PLANT_PRICE", plant.price)
                }
                startActivity(intent)
            },
            onItemLongClick = { plant ->
                // Tampilkan dialog konfirmasi hapus
                showDeleteConfirmationDialog(plant)
            }
        )
        binding.rvPlants.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = plantAdapter
        }
    }

    private fun loadPlants() {
        // Ganti tipe Callback ke PlantResponse
        ApiClient.instance.getAllPlants().enqueue(object : Callback<PlantResponse> {
            // Ganti tipe Response ke PlantResponse
            override fun onResponse(call: Call<PlantResponse>, response: Response<PlantResponse>) {
                if (response.isSuccessful) {
                    // Ekstrak list dari dalam objek response.body()
                    response.body()?.let {
                        plantAdapter.updateData(it.data)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            // Ganti tipe Call ke PlantResponse
            override fun onFailure(call: Call<PlantResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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
                    loadPlants() // Muat ulang daftar tanaman
                } else {
                    Toast.makeText(this@MainActivity, "Gagal menghapus", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang data setiap kali kembali ke activity ini
        loadPlants()
    }
}