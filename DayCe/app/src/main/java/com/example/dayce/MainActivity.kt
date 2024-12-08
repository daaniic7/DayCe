package com.example.dayce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dayce.controller.Controller
import com.example.dayce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usar ViewBinding para inflar la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el controlador
        controller = Controller(this, binding)

        // Configurar el botón flotante para añadir un nuevo aviso
        binding.btnAddAviso.setOnClickListener {
            controller.addAviso()
        }

        // Configurar el botón para cerrar sesión
        binding.btnVolverLogin.setOnClickListener {
            finish() // Cierra esta actividad para volver a la anterior (Login)
        }
    }
}
