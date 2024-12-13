package com.example.dayce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class RegistroActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var btnLastRegister: Button
    private lateinit var editUser: EditText
    private lateinit var editPassword: EditText
    private lateinit var editRepeatPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Inicializar Firebase
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init() // Referenciar componentes
        start() // Configurar interacciones
    }

    private fun init() {
        btnRegister = findViewById(R.id.btn_register_in_register)
        btnLastRegister = findViewById(R.id.btn_last_register)
        editUser = findViewById(R.id.edit_user_register)
        editPassword = findViewById(R.id.edit_pass_register)
        editRepeatPassword = findViewById(R.id.pass_register_repeat_in_register)

        auth = Firebase.auth // Crear objeto de autenticación
    }

    private fun start() {
        btnRegister.setOnClickListener {
            val email = editUser.text.toString()
            val pass = editPassword.text.toString()
            val repeatPass = editRepeatPassword.text.toString()
            if (email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty() || pass != repeatPass) {
                Toast.makeText(this, "Campos vacíos y/o passwords diferentes", Toast.LENGTH_LONG).show()
            } else {
                registerUser(email, pass) { result, msg ->
                    Toast.makeText(this@RegistroActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) startActivityLogin()
                }
            }
        }

        btnLastRegister.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startActivityLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { taskVerification ->
                        val msg = if (taskVerification.isSuccessful) {
                            "Usuario registrado exitosamente. Verifica tu correo electrónico"
                        } else {
                            "Usuario registrado. Error al enviar correo de verificación: ${taskVerification.exception?.message}"
                        }
                        auth.signOut() // Cerrar sesión después del registro
                        onResult(true, msg)
                    }?.addOnFailureListener { exception ->
                        Log.e("RegistroActivity", "Error al enviar correo de verificación: ${exception.message}")
                        onResult(false, "No se pudo enviar el correo de verificación: ${exception.message}")
                    }
                } else {
                    val msg = when (val exception = task.exception) {
                        is FirebaseAuthUserCollisionException -> "Este usuario ya existe"
                        is FirebaseAuthWeakPasswordException -> "Contraseña débil: ${exception.reason}"
                        is FirebaseAuthInvalidCredentialsException -> "El email no es válido"
                        else -> exception?.message ?: "Error desconocido"
                    }
                    onResult(false, msg)
                }
            }
    }
}
