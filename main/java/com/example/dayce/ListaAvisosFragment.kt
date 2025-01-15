package com.example.dayce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dayce.controller.Controller
import com.example.dayce.databinding.FragmentListaAvisosBinding

class ListaAvisosFragment : Fragment() {

    private var _binding: FragmentListaAvisosBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: Controller

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del fragmento usando ViewBinding
        _binding = FragmentListaAvisosBinding.inflate(inflater, container, false)

        // Inicializar el controlador con el RecyclerView
        controller = Controller(requireContext(), binding.myRecyclerView)

        // Configurar el botón flotante para añadir un aviso
        binding.btnAddAviso.setOnClickListener {
            controller.addAviso()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
