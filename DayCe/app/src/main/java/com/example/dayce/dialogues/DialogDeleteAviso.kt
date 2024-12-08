package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogDeleteAviso(
    private val position: Int,
    private val onDeleteAvisoDialog: (Int) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val builder = AlertDialog.Builder(myActivity)
            builder.setMessage("¿Estás seguro que quieres eliminar este aviso?")
                .setPositiveButton("Sí") { _, _ ->
                    onDeleteAvisoDialog(position)
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
