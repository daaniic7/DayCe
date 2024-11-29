package com.example.dayce.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dayce.databinding.ActivityViewBinding
import com.example.dayce.models.Aviso

class ViewAviso(
    view: View,
    var deleteOnClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val binding = ActivityViewBinding.bind(view)

    fun bind(aviso: Aviso, position: Int) {
        binding.tvNombre.text = aviso.nombre
        binding.tvCiudad.text = aviso.direccion
        binding.tvFecha.text = aviso.fecha
        binding.tvDescripcion.text = aviso.descripcion

        // Load image using Glide
        if (aviso.imagen.isNotEmpty()) {
            Glide
                .with(itemView.context)
                .load(aviso.imagen)
                .centerCrop()
                .into(binding.ivFoto)
        } else {
            binding.ivFoto.setImageResource(R.drawable.default_image) // Set a placeholder image
        }

        // Set listener for delete action
        binding.ivBorrar.setOnClickListener {
            deleteOnClick(position)
        }
    }
}
