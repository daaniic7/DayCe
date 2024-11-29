package com.example.dayce.dao

import com.example.dayce.interfaces.InterfazDAO
import com.example.dayce.models.Aviso
import com.example.dayce.objects_models.Datos

class AvisoDAO private constructor() : InterfazDAO {
    companion object {
        val myDao: AvisoDAO by lazy { //lazy delega a un primer acceso
            AvisoDAO() //Me creo sólo este objeto una vez.
        }
    }

    //Método que accede a la BBDD y devuelve todos los datos
    override
    fun getDataMonuments(): List<Aviso> = Datos().listAvisos
}