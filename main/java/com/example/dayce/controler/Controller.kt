package com.example.dayce.controller

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dayce.adapter.AdapterAviso
import com.example.dayce.dao.AvisoDAO
import com.example.dayce.dialogues.DialogAddAviso
import com.example.dayce.dialogues.DialogDeleteAviso
import com.example.dayce.dialogues.DialogEditAviso
import com.example.dayce.models.Aviso

class Controller(
    private val context: Context,
    private val recyclerView: RecyclerView
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
            { position -> editAviso(position) },   // Editar aviso
            { aviso -> showAvisoDetails(aviso) }  // Mostrar detalles del aviso
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun deleteAviso(position: Int) {
        val dialog = DialogDeleteAviso(position) { pos ->
            val nombreAviso = listaAvisos[pos].nombre
            listaAvisos.removeAt(pos)
            recyclerView.adapter?.notifyItemRemoved(pos)
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogDeleteAviso")
    }

    private fun editAviso(position: Int) {
        val aviso = listaAvisos[position]
        val dialog = DialogEditAviso(aviso) { updatedAviso ->
            listaAvisos[position] = updatedAviso
            recyclerView.adapter?.notifyItemChanged(position)
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogEditAviso")
    }

    private fun showAvisoDetails(aviso: Aviso) {
        AlertDialog.Builder(context)
            .setTitle("Detalles del Aviso")
            .setMessage(
                "Nombre: ${aviso.nombre}\n" +
                        "Dirección: ${aviso.direccion}\n" +
                        "Fecha: ${aviso.fecha}\n" +
                        "Descripción: ${aviso.descripcion}"
            )
            .setPositiveButton("Cerrar", null)
            .show()
    }

    fun addAviso() {
        val dialog = DialogAddAviso { newAviso ->
            listaAvisos.add(newAviso)
            recyclerView.adapter?.notifyItemInserted(listaAvisos.size - 1)
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogAddAviso")
    }
}
