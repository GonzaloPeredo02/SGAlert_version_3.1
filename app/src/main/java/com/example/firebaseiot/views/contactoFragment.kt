package com.example.firebaseiot.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseiot.R
import com.example.firebaseiot.databinding.FragmentContactoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseiot.EditarContactoActivity
import com.example.firebaseiot.Models.Contacto
import com.example.firebaseiot.adapter.AdapterContacto
import com.example.firebaseiot.crearContactoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class contactoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    // declarar ViewBinding
    private lateinit var binding: FragmentContactoBinding

    // declarar la base de datos
    private lateinit var database: DatabaseReference

    //uso para poder trabajar con la view list
    //lista de contactos
    private lateinit var contactosList: ArrayList<Contacto>
    //declarar adaptador para recorrer lista
    private lateinit var adapterContacto: AdapterContacto
    //recycler view- para cargarlos dentro del recycle
    private lateinit var contactoRecycleView: RecyclerView

    // declarar FirebaseAuth
    private lateinit var auth: FirebaseAuth

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
        binding = FragmentContactoBinding.inflate(inflater, container, false)
        val view = binding.root

        // inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        // inicializar la base de datos bajo el ID del usuario
        database = FirebaseDatabase.getInstance().getReference("emergencyContacts").child(userId!!)

        //mostrar los datos en la pantalla
        contactoRecycleView = binding.rvProductos
        //permite trabajar con layout manager
        contactoRecycleView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        //evitar error visual.
        contactoRecycleView.hasFixedSize()
        contactosList = arrayListOf()

        //cargar los contactos
        getContactos()

        // Configuracion del botón para ir a la actividad crear contacto!!
        binding.btnCrearContacto.setOnClickListener {
            val intent = Intent(activity, crearContactoActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getContactos() {
        //conexion a base de datos
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //cuando el dato cambia la pantalla refresca los cambios
                if (snapshot.exists()) {
                    contactosList.clear()
                    //se toman los contactos de la base de datos
                    for (contactosSnapshot in snapshot.children) {
                        val contacto = contactosSnapshot.getValue(Contacto::class.java)
                        //agregan a la lista
                        contacto?.let { contactosList.add(it) }
                    }
                    //se pasa al adaptador, y este se encarga de tomar los datos
                    //actualizara la pantalla
                    adapterContacto = AdapterContacto(contactosList, this@contactoFragment::onEditClick, this@contactoFragment::onDeleteClick)
                    contactoRecycleView.adapter = adapterContacto
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores
            }
        })
    }
    //Al hacer click al boton editar manda a la actividad editar contacto
    private fun onEditClick(contacto: Contacto) {
        val intent = Intent(context, EditarContactoActivity::class.java)
        intent.putExtra("contacto", contacto)
        startActivity(intent)
    }


    private fun onDeleteClick(contacto: Contacto) {
        // Implementar la lógica para eliminar el contacto
        database.child(contacto.id!!).removeValue().addOnCompleteListener {
            Toast.makeText(context, "Contacto eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            contactoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


