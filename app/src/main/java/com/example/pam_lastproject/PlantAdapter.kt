package com.example.pam_lastproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(
    private val plants: MutableList<Plant>,
    private val onItemClick: (Plant) -> Unit,
    private val onItemLongClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.bind(plant)
        holder.itemView.setOnClickListener { onItemClick(plant) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(plant)
            true
        }
    }

    override fun getItemCount(): Int = plants.size

    fun updateData(newPlants: List<Plant>) {
        plants.clear()
        plants.addAll(newPlants)
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_plant_name)
        private val description: TextView = itemView.findViewById(R.id.tv_plant_description)
        private val price: TextView = itemView.findViewById(R.id.tv_plant_price)

        fun bind(plant: Plant) {
            name.text = plant.plant_name
            description.text = plant.description
            price.text = "Harga: Rp ${plant.price}"
        }
    }
}