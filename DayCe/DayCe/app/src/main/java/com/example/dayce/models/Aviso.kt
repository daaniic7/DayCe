package com.example.dayce.models

data class Aviso(var nombre: String,
                  var direccion: String,
                  var fecha: String,
                  var descripcion: String,
                  var imagen: String){
    override fun toString(): String {
        return "Aviso(nombre='$nombre', ciudad='$direccion', fecha=$fecha, descripcion='$descripcion', imagen='$imagen')"
    }
}

