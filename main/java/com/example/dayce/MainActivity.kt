package com.example.dayce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.dayce.controller.Controller
import com.example.dayce.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usar ViewBinding para inflar la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Toolbar
        setSupportActionBar(binding.toolbar)

        // Inicializar el DrawerLayout y el NavigationView
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // Configurar el ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar listener para el menú lateral
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showFragment(InicioFragment())
                R.id.nav_about_us -> showFragment(ListaAvisosFragment())
                R.id.nav_logout -> handleLogout()
                else -> Toast.makeText(this, "Opción no reconocida", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Configurar el BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(InicioFragment())
                    true
                }
                R.id.nav_stories -> {
                    showFragment(ListaAvisosFragment())
                    true
                }
                R.id.nav_logout -> {
                    handleLogout() // Manejar el Logout
                    true
                }
                else -> false
            }
        }
    }

    // Método para manejar el Logout
    private fun handleLogout() {
        FirebaseAuth.getInstance().signOut() // Cerrar sesión de Firebase
        startActivity(Intent(this, Login::class.java)) // Redirigir al Login
        finish() // Finalizar la actividad actual
    }

    // Método para mostrar un fragmento en el FrameLayout
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    // Controlar el botón "Atrás" para cerrar el Drawer si está abierto
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
