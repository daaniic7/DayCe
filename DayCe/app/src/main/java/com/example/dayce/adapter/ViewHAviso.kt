package com.example.dayce.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dayce.databinding.ItemAvisoBinding
import com.example.dayce.models.Aviso

class ViewHAviso(
    private val binding: ItemAvisoBinding,
    private val deleteOnClick: (Int) -> Unit,
    private val editOnClick: (Int) -> Unit // Nuevo parámetro
) : RecyclerView.ViewHolder(binding.root) {

    fun renderize(aviso: Aviso) {
        binding.nombreCliente.text = aviso.nombre
        binding.descripcionAviso.text = aviso.descripcion
        binding.direccionAviso.text = aviso.direccion
        binding.fechaAviso.text = aviso.fecha

        Glide.with(itemView.context)
            .load(aviso.imagen)
            .centerCrop()
            .into(binding.imagenAviso)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.buttonBorrar.setOnClickListener {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                deleteOnClick(bindingAdapterPosition)
            }
        }

        binding.buttonEditarAviso.setOnClickListener { // Botón para editar
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                editOnClick(bindingAdapterPosition)
            }
        }
    }
}
