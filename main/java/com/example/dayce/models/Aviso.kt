package com.example.dayce.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Aviso(
    var nombre: String,
    var direccion: String,
    var fecha: String,
    var descripcion: String,
    var imagen: String
) : Parcelable {
    override fun toString(): String {
        return "Aviso(nombre='$nombre', direccion='$direccion', fecha=$fecha, descripcion='$descripcion', imagen='$imagen')"
    }
}

