package com.example.pam_lastproject

import android.content.Intent // Pastikan Intent di-import
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_lastproject.databinding.ActivityPlantDetailBinding

class PlantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantId = intent.getIntExtra("PLANT_ID", -1)
        val plantName = intent.getStringExtra("PLANT_NAME")
        val plantDesc = intent.getStringExtra("PLANT_DESC")
        val plantPrice = intent.getStringExtra("PLANT_PRICE")

        supportActionBar?.title = plantName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tvDetailPlantName.text = plantName
        binding.tvDetailPlantDescription.text = plantDesc
        binding.tvDetailPlantPrice.text = "Harga: Rp $plantPrice"
        binding.btnUpdate.setOnClickListener {
            // Buat Intent untuk membuka AddEditPlantActivity
            val intent = Intent(this, AddEditPlantActivity::class.java).apply {
                // Kirim semua data tanaman ke halaman edit
                putExtra("PLANT_ID", plantId)
                putExtra("PLANT_NAME", plantName)
                putExtra("PLANT_DESC", plantDesc)
                putExtra("PLANT_PRICE", plantPrice)
            }
            startActivity(intent)
        }
    }

    // Cara yang benar untuk menangani klik pada tombol kembali di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        // Ini akan berfungsi seperti tombol 'back' standar
        finish()
        return true
    }
}