package com.example.dayce.interfaces

import com.example.dayce.models.Aviso

interface InterfazDAO{
    fun getDataAvisos(): List<Aviso>
}