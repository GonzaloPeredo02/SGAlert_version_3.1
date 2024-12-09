package com.example.firebaseiot.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseiot.Emergencia
import com.example.firebaseiot.databinding.FragmentInicioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [inicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class inicioFragment : Fragment() {

    // declarar ViewBinding
    private lateinit var binding: FragmentInicioBinding
    // declarar FirebaseAuth
    private lateinit var auth: FirebaseAuth
    // declarar DatabaseReference
    private lateinit var database: DatabaseReference

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        // inicializar DatabaseReference
        database = FirebaseDatabase.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar para poder manipular
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configurar el botón de emergencia
        binding.btnEmergencia.setOnClickListener {
            val intent = Intent(activity, Emergencia::class.java)
            startActivity(intent)
        }

        // Recuperar y mostrar los datos del usuario
        val userId = auth.currentUser?.uid
        val userRef = database.child("users").child(userId!!)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                //modificar para que muestre nombre: y el dato. etc.
                binding.tvNombre.text = "Nombre: ${user?.nombre}"
                binding.tvApellido.text = "Apellido: ${user?.apellido}"
                binding.tvEmail.text = "Correo: ${user?.email}"
                binding.tvTelefono.text = "Teléfono: ${user?.telefono}"
                binding.tvDireccion.text = "Dirección: ${user?.direccion}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment inicioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            inicioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

// Clase de datos para el usuario
data class User(
    val userId: String? = "",
    val nombre: String? = "",
    val apellido: String? = "",
    val email: String? = "",
    val telefono: String? = "",
    val direccion: String? = ""
) {
    // Constructor sin argumentos
    constructor() : this("", "", "", "", "", "")
}
