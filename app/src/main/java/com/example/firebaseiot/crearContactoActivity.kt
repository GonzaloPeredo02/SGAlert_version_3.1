package com.example.firebaseiot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseiot.Models.Contacto
import com.example.firebaseiot.databinding.ActivityCrearContactoBinding
import com.example.firebaseiot.views.contactoFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class crearContactoActivity : AppCompatActivity() {

    //activar viewbinding
    private lateinit var binding: ActivityCrearContactoBinding
    //activar database realtime
    private lateinit var database: DatabaseReference
    //activar FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // declarar ViewBinding
        binding = ActivityCrearContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        // obtener el ID del usuario activo
        val userId = auth.currentUser?.uid

        // declarar la base de datos bajo el ID del usuario
        database = FirebaseDatabase.getInstance().getReference("emergencyContacts").child(userId!!)

        binding.btnGuardar.setOnClickListener {
            //obtener los datos
            val nombre = binding.etNombreContacto.text.toString()
            val correo = binding.etCorreoContacto.text.toString()
            val numero = binding.etNumeroContacto.text.toString()
            //generar id de forma random
            val id = database.push().key

            //preguntar si los campos no esten vacios
            if (nombre.isEmpty()){
                binding.etNombreContacto.error = "por favor ingresa un nombre"
            }
            if (correo.isEmpty()){
                binding.etCorreoContacto.error = "por favor ingresa un correo"
            }
            if (numero.isEmpty()){
                binding.etNumeroContacto.error = "por favor ingresa un numero"
            }
            //me lleva a Models.Contacto (referencia)
            val contacto = Contacto (id, nombre,correo,numero)
            //cuando la accion sea escuchada correctamente
            database.child(id!!).setValue(contacto).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Limpiar los campos de entrada
                    binding.etNombreContacto.setText("")
                    binding.etCorreoContacto.setText("")
                    binding.etNumeroContacto.setText("")
                    // Mostrar un mensaje de confirmación
                    Toast.makeText(this, "Contacto Guardado", Toast.LENGTH_SHORT).show()

                    // Redirigir al fragmento contactoFragment
                    val fragment = contactoFragment()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                } else {
                    Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show()
                }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
}




