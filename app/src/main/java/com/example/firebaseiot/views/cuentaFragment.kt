package com.example.firebaseiot.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseiot.R
import com.example.firebaseiot.databinding.FragmentCuentaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseiot.MainActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [cuentaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class cuentaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Declarar FirebaseAuth y el binding
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento usando View Binding
        //nota propia.Los fragmentos requieren parámetros adicionales (container y false) para el inflado correcto.
        binding = FragmentCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar el botón de cerrar sesión
        binding.btnCerrarSesion.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("cerrar sesión")
                .setMessage("¿estás seguro  que deseas cerrar sesión?")
                .setNeutralButton("cancelar") { dialog, which ->
                    // cancelar cierre de sesion
                }
                .setPositiveButton("aceptar") { dialog, which ->
                    // configuracion para cerrar sesion
                    auth.signOut()

                    // Actualizar el estado de la sesión en SharedPreferences
                    //evita que al cerrar la sesion y volver abrirla quede logeada aun
                    val sharedPref = requireActivity().getSharedPreferences("MyApp", AppCompatActivity.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("isLoggedIn", false)
                    editor.apply()

                    // Redirigir a la pantalla de inicio de sesión
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CuentaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cuentaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




