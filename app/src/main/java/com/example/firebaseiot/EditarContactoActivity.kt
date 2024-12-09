package com.example.firebaseiot

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseiot.Models.Contacto
import com.example.firebaseiot.databinding.ActivityEditarContactoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditarContactoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarContactoBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var contacto: Contacto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        // Obtener el contacto pasado como extra
        contacto = intent.getParcelableExtra("contacto")!!

        // Inicializar la base de datos bajo el ID del usuario y el ID del contacto
        database = FirebaseDatabase.getInstance().getReference("emergencyContacts").child(userId!!).child(contacto.id!!)

        // Mostrar los datos del contacto en los campos de texto
        binding.etNombreContacto.setText(contacto.nombre)
        binding.etCorreoContacto.setText(contacto.correo)
        binding.etNumeroContacto.setText(contacto.numero)

        // Configurar el botón de guardar
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombreContacto.text.toString()
            val correo = binding.etCorreoContacto.text.toString()
            val numero = binding.etNumeroContacto.text.toString()

            // Actualizar los datos del contacto
            val updatedContacto = Contacto(contacto.id, nombre, correo, numero)
            database.setValue(updatedContacto).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    finish() // Cerrar la actividad después de guardar los cambios
                } else {
                    // Manejar el error
                    Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
