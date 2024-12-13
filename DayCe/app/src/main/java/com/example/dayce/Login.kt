package com.example.dayce

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Login : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnRecoverPass: Button
    private lateinit var editUser: EditText
    private lateinit var editPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Inicializar Firebase
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        init() // Referenciar componentes
        start() // Configurar interacciones
    }

    private fun init() {
        btnLogin = findViewById(R.id.btn_login_in_login)
        btnRegister = findViewById(R.id.btn_register_in_login)
        btnRecoverPass = findViewById(R.id.btn_recover_pass)
        editUser = findViewById(R.id.edit_user_login)
        editPassword = findViewById(R.id.edit_pass_login)
        auth = Firebase.auth // Crear objeto de autenticación
    }

    private fun start() {
        btnLogin.setOnClickListener {
            val user = editUser.text.toString()
            val pass = editPassword.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                startLogin(user, pass) { result, msg ->
                    Toast.makeText(this@Login, msg, Toast.LENGTH_LONG).show()
                    if (result) {
                        saveLoginState(true) // Guarda el estado del inicio de sesión
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Tienes algún campo vacío", Toast.LENGTH_LONG).show()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnRecoverPass.setOnClickListener {
            val user = editUser.text.toString()
            if (user.isNotEmpty()) {
                recoverPassword(user) { result, msg ->
                    Toast.makeText(this@Login, msg, Toast.LENGTH_LONG).show()
                    if (!result) editUser.setText("")
                }
            } else {
                Toast.makeText(this, "Debes rellenar el campo email", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun recoverPassword(email: String, onResult: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { taskResetEmail ->
                if (taskResetEmail.isSuccessful) {
                    onResult(true, "Acabamos de enviarte un email con la nueva contraseña")
                } else {
                    val msg = when (val exception = taskResetEmail.exception) {
                        is FirebaseAuthInvalidCredentialsException -> "El formato del email es incorrecto"
                        else -> exception?.message ?: "Error de reseteo inesperado"
                    }
                    onResult(false, msg)
                }
            }
    }

    private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser?.isEmailVerified == true) {
                        onResult(true, "BIENVENIDO")
                    } else {
                        auth.signOut()
                        onResult(false, "Debes verificar tu correo antes de loguearte")
                    }
                } else {
                    val msg = when (val exception = task.exception) {
                        is FirebaseAuthInvalidUserException -> "El usuario tiene problemas por haberse borrado o deshabilitado"
                        is FirebaseAuthInvalidCredentialsException -> {
                            if (exception.message?.contains("There is no user record") == true) {
                                "Error"
                            } else "El usuario no existe"
                        }
                        else -> exception?.message ?: "Error desconocido"
                    }
                    onResult(false, msg)
                }
            }
    }

    /**
     * Preferencias compartidas para asegurarse que al cerrar la aplicación seguiremos
     * logueados
     */
    private fun saveLoginState(loggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", loggedIn)
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }
}
