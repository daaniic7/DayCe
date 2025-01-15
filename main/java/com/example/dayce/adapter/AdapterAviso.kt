package com.example.dayce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dayce.databinding.ItemAvisoBinding
import com.example.dayce.models.Aviso

class AdapterAviso(
    private val listaAviso: MutableList<Aviso>,
    private val deleteOnClick: (Int) -> Unit,
    private val editOnClick: (Int) -> Unit,
    private val itemOnClick: (Aviso) -> Unit // Callback para clic en el item
) : RecyclerView.Adapter<AdapterAviso.ViewHAviso>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAviso {
        val binding = ItemAvisoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHAviso(binding, deleteOnClick, editOnClick, itemOnClick)
    }

    override fun onBindViewHolder(holder: ViewHAviso, position: Int) {
        holder.renderize(listaAviso[position])
    }

    override fun getItemCount(): Int = listaAviso.size

    // Clase ViewHolder interna
    inner class ViewHAviso(
        private val binding: ItemAvisoBinding,
        private val deleteOnClick: (Int) -> Unit,
        private val editOnClick: (Int) -> Unit,
        private val itemOnClick: (Aviso) -> Unit // Callback para clic en el item
    ) : RecyclerView.ViewHolder(binding.root) {

        fun renderize(aviso: Aviso) {
            // Configurar datos en el item
            binding.nombreCliente.text = aviso.nombre
            binding.direccionAviso.text = aviso.direccion
            binding.fechaAviso.text = aviso.fecha
            binding.descripcionAviso.text = aviso.descripcion

            // Configurar clic en el botón de borrar
            binding.buttonBorrar.setOnClickListener {
                deleteOnClick(adapterPosition)
            }

            // Configurar clic en el botón de editar
            binding.buttonEditarAviso.setOnClickListener {
                editOnClick(adapterPosition)
            }

            // Configurar clic en el item completo
            binding.root.setOnClickListener {
                itemOnClick(aviso)
            }
        }
    }
}
