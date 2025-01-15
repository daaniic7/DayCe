package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dayce.R
import com.example.dayce.databinding.DialogAddAvisoBinding
import com.example.dayce.models.Aviso

class DialogAddAviso(
    private val onNewAvisoDialog: (Aviso) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val builder = AlertDialog.Builder(myActivity)
            val inflater = myActivity.layoutInflater
            val viewDialog = inflater.inflate(R.layout.dialog_add_aviso, null)
            builder.setView(viewDialog)
                .setMessage("Añadir Aviso")
                .setPositiveButton("Aceptar") { _, _ ->
                    val aviso = recoverDataLayout(viewDialog)
                    if (validacion(aviso)) {
                        onNewAvisoDialog(aviso)
                        Toast.makeText(myActivity, "Aviso creado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(myActivity, "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    Toast.makeText(myActivity, "Añadir cancelado", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
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

    private fun recoverDataLayout(view: View): Aviso {
        val binding = DialogAddAvisoBinding.bind(view)
        return Aviso(
            nombre = binding.etNombre.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            fecha = binding.etFecha.text.toString(),
            descripcion = binding.etDescripcion.text.toString(),
            imagen = binding.etImagen.text.toString()
        )
    }
}
