package com.example.dayce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dayce.databinding.FragmentDetallesAvisoBinding
import com.example.dayce.models.Aviso

class DetallesAvisoFragment : Fragment() {

    private var _binding: FragmentDetallesAvisoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesAvisoBinding.inflate(inflater, container, false)

        // Obtener los datos del aviso desde los argumentos
        val aviso = arguments?.getParcelable<Aviso>("aviso")

        // Mostrar los datos del aviso en los elementos de la vista
        aviso?.let {
            binding.textNombreCliente.text = it.nombre
            binding.textDireccionAviso.text = it.direccion
            binding.textFechaAviso.text = it.fecha
            binding.textDescripcionAviso.text = it.descripcion
        }

        // Configurar la flecha de navegación para volver
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detalles del Aviso" // Opcional, cambiar el título del ActionBar
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Restablecer la flecha de navegación al estado predeterminado
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
