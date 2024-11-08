package com.example.dayce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Se recuperan el usuario y la contraseña introducidos en el login
        val usuario = intent.getStringExtra("usuario")
        val contrasena = intent.getStringExtra("contrasena")


        val tvUsuario = findViewById<TextView>(R.id.tv_usuario)
        val tvContrasena = findViewById<TextView>(R.id.tv_contrasena)

        if (usuario != null) {
            tvUsuario.text = usuario
        }
        if (contrasena != null) {
            tvContrasena.text = contrasena
        }

        //Botón para Cerrar sesión y volver al Login
        val btnVolverLogin = findViewById<Button>(R.id.btn_volver_login)
        btnVolverLogin.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}

