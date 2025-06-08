package com.example.pam_lastproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PlantAdapter(
    private val plants: MutableList<Plant>,
    private val onEditClick: (Plant) -> Unit,      // Listener untuk klik seluruh item (Edit)
    private val onDeleteClick: (Plant) -> Unit,    // Listener untuk tombol Hapus
    private val onDetailClick: (Plant) -> Unit     // Listener untuk tombol Detail
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.bind(plant)
    }

    override fun getItemCount(): Int = plants.size

    fun updateData(newPlants: List<Plant>) {
        plants.clear()
        plants.addAll(newPlants)
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inisialisasi semua view dari layout item_plant.xml
        private val name: TextView = itemView.findViewById(R.id.tv_plant_name)
        private val price: TextView = itemView.findViewById(R.id.tv_plant_price)
        private val image: ImageView = itemView.findViewById(R.id.iv_plant_image)
        private val deleteButton: Button = itemView.findViewById(R.id.btn_delete)
        private val detailButton: Button = itemView.findViewById(R.id.btn_detail)

        fun bind(plant: Plant) {
            name.text = plant.plant_name
            price.text = "Rp ${plant.price}"
            Glide.with(itemView.context)
                .load(R.drawable.ic_plant) // Ganti dengan URL gambar jika ada
                .into(image)

            // 1. Set listener untuk EDIT pada seluruh item view
            // Ini akan memicu fungsi edit saat area kartu di-klik
            itemView.setOnClickListener { onEditClick(plant) }

            // 2. Set listener untuk DELETE pada tombol Hapus
            deleteButton.setOnClickListener { onDeleteClick(plant) }

            // 3. Set listener untuk DETAIL pada tombol Detail
            detailButton.setOnClickListener { onDetailClick(plant) }
        }
    }
}