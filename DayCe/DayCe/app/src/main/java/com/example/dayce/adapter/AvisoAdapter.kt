package com.example.dayce.adapter

import com.example.dayce.models.Aviso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dayce.R

class AvisoAdapter(
    private val items: MutableList<Aviso>,
    private val onDelete: (Aviso) -> Unit
) : RecyclerView.Adapter<AvisoAdapter.AvisoViewHolder>() {

    class AvisoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.aviso_nombre)
        val direccion: TextView = view.findViewById(R.id.aviso_direccion)
        val fecha: TextView = view.findViewById(R.id.aviso_fecha)
        val descripcion: TextView = view.findViewById(R.id.aviso_descripcion)
        val imagen: ImageView = view.findViewById(R.id.aviso_imagen)
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aviso, parent, false)
        return AvisoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvisoViewHolder, position: Int) {
        val aviso = items[position]
        holder.nombre.text = aviso.nombre
        holder.direccion.text = aviso.direccion
        holder.fecha.text = aviso.fecha
        holder.descripcion.text = aviso.descripcion
        Glide.with(holder.itemView.context).load(aviso.imagen).into(holder.imagen)
        holder.deleteButton.setOnClickListener {
            onDelete(aviso)
        }
    }

    override fun getItemCount(): Int = items.size
}

