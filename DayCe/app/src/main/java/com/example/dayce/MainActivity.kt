package com.example.dayce

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dayce.controller.Controller
import com.example.dayce.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

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
            logout()
        }
    }

    private fun logout() {
        // Cerrar sesión de Firebase
        FirebaseAuth.getInstance().signOut()

        // Actualizar el estado de inicio de sesión en SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", false)
        editor.apply()

        // Redirigir al Login
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Finalizar la actividad actual
    }
}
