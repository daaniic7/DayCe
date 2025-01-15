package com.example.dayce.dao

import com.example.dayce.objects_models.Datos

object AvisoDAO {
    fun getDataAvisos() = Datos.listaAvisos // Acceso directo a los datos desde el objeto Datos
}
