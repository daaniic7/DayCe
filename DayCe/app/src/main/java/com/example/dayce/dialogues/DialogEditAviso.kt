package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dayce.databinding.DialogEditarAvisoBinding
import com.example.dayce.models.Aviso

class DialogEditAviso(
    private val avisoToEdit: Aviso,
    private val onEditAvisoDialog: (Aviso) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val inflater = myActivity.layoutInflater
            val binding = DialogEditarAvisoBinding.inflate(inflater)
            binding.etNombre.setText(avisoToEdit.nombre)
            binding.etDireccion.setText(avisoToEdit.direccion)
            binding.etFecha.setText(avisoToEdit.fecha)
            binding.etDescripcion.setText(avisoToEdit.descripcion)
            binding.etImagen.setText(avisoToEdit.imagen)

            val builder = AlertDialog.Builder(myActivity)
            builder.setView(binding.root)
                .setTitle("Editar Aviso")
                .setPositiveButton("Guardar") { _, _ ->
                    val updatedAviso = recoverDataLayout(binding)
                    if (validacion(updatedAviso)) {
                        onEditAvisoDialog(updatedAviso)
                    } else {
                        Toast.makeText(myActivity, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun validacion(aviso: Aviso): Boolean {
        return aviso.nombre.isNotEmpty() &&
                aviso.direccion.isNotEmpty() &&
                aviso.fecha.isNotEmpty() &&
                aviso.descripcion.isNotEmpty() &&
                aviso.imagen.isNotEmpty()
    }

    private fun recoverDataLayout(binding: DialogEditarAvisoBinding): Aviso {
        return Aviso(
            nombre = binding.etNombre.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            fecha = binding.etFecha.text.toString(),
            descripcion = binding.etDescripcion.text.toString(),
            imagen = binding.etImagen.text.toString()
        )
    }
}
