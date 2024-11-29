package com.example.dayce

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayce.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var fichero_compartido : SharedPreferences
    private lateinit var intent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        iniciarPCompartidas()


        loginBinding.btEntrar.setOnClickListener{
            //USUARIO Y CONTRASEÑA
            val USER = "danielcornejo"
            val PASSWORD = "danielcornejo"

            val usuario = loginBinding.usuario.text.toString()
            val contasena = loginBinding.contrasena.text.toString()

            //Si no rellenamos los campos usuario y contraseña nos saldrá este Toast
            //con el mensaje que se muestra aquí
            if (usuario.isBlank() || contasena.isBlank()) {
                Toast.makeText(this, "Hay campos que están vacios", Toast.LENGTH_LONG).show()
            } else if (usuario == USER && contasena == PASSWORD) {
                intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("usuario",usuario)
                    putExtra("contrasena", contasena)
                }
                startActivity(intent)
           //Si el usuario y/o contraseña son incorrectos nos saldrá este mensaje dentro de un Toast
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            }

        }
    }
    //Función para iniciar las preferencias compartidas
    private fun iniciarPCompartidas(){
        val nombreFicheroCompartido = getString(R.string.fichero_preferencia_compartida)

        this.fichero_compartido = getSharedPreferences(nombreFicheroCompartido, MODE_PRIVATE)
    }

}