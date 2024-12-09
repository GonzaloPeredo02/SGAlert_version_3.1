package com.example.firebaseiot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseiot.databinding.ActivityRegistrarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistrarActivity : AppCompatActivity() {

    //configuracion de  viewbinding
    private lateinit var binding: ActivityRegistrarBinding
    //configurar firebase
    private lateinit var auth: FirebaseAuth
    //configurar database
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inicializar firebase con auth
        auth = Firebase.auth
        //inicializar database
        database = FirebaseDatabase.getInstance().reference

        //tomar datos para registrar
        binding.btnRegistrar.setOnClickListener {
            // Obtener el correo y contraseñas ingresadas o en pantalla
            val email = binding.etEmail.text.toString()
            val pass1 = binding.etPassword.text.toString()
            val pass2 = binding.etPassword2.text.toString()
            val nombre = binding.etNombre.text.toString()
            val apellido = binding.etApellido.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val direccion = binding.etDireccion.text.toString()
            //se crea la var is valid, cuando sea true dejara pasar los dato,
            //mientras sea cualquier error sera false, entonces no dejara avanzar.
            var isValid = true

            if (email.isEmpty()) {
                binding.etEmail.error = "Por favor ingrese un correo"
                isValid = false
            }
            if (pass1.isEmpty()) {
                binding.etPassword.error = "Por favor ingrese una contraseña"
                isValid = false
            }
            if (pass2.isEmpty()) {
                binding.etPassword2.error = "Por favor ingrese una contraseña"
                isValid = false
            }
            // Validar que ambas contraseñas sean iguales
            if (pass1 != pass2) {
                binding.etPassword2.error = "Las contraseñas no coinciden"
                isValid = false
            }
            if (nombre.isEmpty()) {
                binding.etNombre.error = "Por favor ingrese un nombre"
                isValid = false
            }
            if (apellido.isEmpty()) {
                binding.etApellido.error = "Por favor ingrese un apellido"
                isValid = false
            }
            if (telefono.isEmpty()) {
                binding.etTelefono.error = "Por favor ingrese un teléfono"
                isValid = false
            }
            if (direccion.isEmpty()) {
                binding.etDireccion.error = "Por favor ingrese una dirección"
                isValid = false
            }

            if (isValid) {
                // Mandar los datos de email, pass1, nombre, apellido, teléfono y dirección
                signUp(email, pass1, nombre, apellido, telefono, direccion)
            } else {
                // toast para indicar que se produjieron errores
                Toast.makeText(this, "se ha producido un error, porfavor revise los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(email: String, pass1: String, nombre: String, apellido: String, telefono: String, direccion: String) {
        //se almacenan los datos recibidos email y pass1 y se trabaja con estos.
        auth.createUserWithEmailAndPassword(email, pass1)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = User(userId, nombre, apellido, email, telefono, direccion)
                    database.child("users").child(userId!!).setValue(user)
                        .addOnCompleteListener {
                            //creacion de toast para que informe en pantalla el registro exitoso
                            Toast.makeText(
                                this, "usuario registrado",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                } else {
                    //creacion de toast para dar informacion de que hubo error durante el registro
                    Toast.makeText(this, "error en el registro de usuario", Toast.LENGTH_LONG).show()
                }
            }
    }
}

// Clase de datos para el usuario
data class User(val userId: String?, val nombre: String, val apellido: String, val email: String, val telefono: String, val direccion: String)
