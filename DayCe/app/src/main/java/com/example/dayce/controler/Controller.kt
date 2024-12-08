package com.example.dayce.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dayce.adapter.AdapterAviso
import com.example.dayce.dao.AvisoDAO
import com.example.dayce.databinding.ActivityMainBinding
import com.example.dayce.dialogues.DialogAddAviso
import com.example.dayce.dialogues.DialogDeleteAviso
import com.example.dayce.dialogues.DialogEditAviso
import com.example.dayce.models.Aviso

class Controller(
    private val context: Context,
    private val binding: ActivityMainBinding
) {

    private lateinit var listaAvisos: MutableList<Aviso>

    init {
        initData()
        setAdapter()
    }

    private fun initData() {
        listaAvisos = AvisoDAO.getDataAvisos().toMutableList() // Acceso correcto y mutable
    }

    private fun setAdapter() {
        val adapter = AdapterAviso(
            listaAvisos,
            { position -> deleteAviso(position) },  // Eliminar aviso
            { position -> editAviso(position) }    // Editar aviso
        )
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.adapter = adapter
    }

    private fun deleteAviso(position: Int) {
        val dialog = DialogDeleteAviso(position) { pos ->
            val nombreAviso = listaAvisos[pos].nombre
            listaAvisos.removeAt(pos)
            binding.myRecyclerView.adapter?.notifyItemRemoved(pos)
            Toast.makeText(context, "Aviso eliminado: $nombreAviso", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogDeleteAviso")
    }

    private fun editAviso(position: Int) {
        val aviso = listaAvisos[position]
        val dialog = DialogEditAviso(aviso) { updatedAviso ->
            // Actualizar los datos del aviso con los valores editados
            listaAvisos[position] = updatedAviso
            binding.myRecyclerView.adapter?.notifyItemChanged(position)
            Toast.makeText(context, "Aviso actualizado", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogEditAviso")
    }

    fun addAviso() {
        val dialog = DialogAddAviso { newAviso ->
            listaAvisos.add(newAviso)
            binding.myRecyclerView.adapter?.notifyItemInserted(listaAvisos.size - 1)
            Toast.makeText(context, "Nuevo aviso añadido", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogAddAviso")
    }
}
