package com.example.dayce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dayce.databinding.ItemAvisoBinding
import com.example.dayce.models.Aviso

class AdapterAviso(
    private val listaAviso: MutableList<Aviso>,
    private val deleteOnClick: (Int) -> Unit,
    private val editOnClick: (Int) -> Unit
) : RecyclerView.Adapter<ViewHAviso>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAviso {
        val binding = ItemAvisoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHAviso(binding, deleteOnClick, editOnClick)
    }

    override fun onBindViewHolder(holder: ViewHAviso, position: Int) {
        holder.renderize(listaAviso[position])
    }

    override fun getItemCount(): Int = listaAviso.size
}


