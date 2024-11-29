package com.example.dayce.dialogues

import androidx.fragment.app.DialogFragment

class DialogDeleteAviso(
    val pos: Int, // posición del aviso
    val onDeleteAvisoDialog: (Int) -> Unit // función de eliminación
) : DialogFragment() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        // Llama directamente a la función de eliminación
        onDeleteAvisoDialog(pos)

        // Cierra el fragmento de diálogo automáticamente
        dismiss()
    }
}
