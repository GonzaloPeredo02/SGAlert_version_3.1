package com.example.firebaseiot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseiot.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //declarar variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = Firebase.auth

        // Verificar el estado de la sesión
        val sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Redirigir a la actividad principal si el usuario ya ha iniciado sesión
            val intent = Intent(this, ActivityNavigator::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val correo = binding.etEmail.text.toString()
            val contrasena = binding.etPassword.text.toString()
            if (correo.isEmpty()) {
                binding.etEmail.error = "Ingrese un correo"
                return@setOnClickListener
            }
            if (contrasena.isEmpty()) {
                binding.etPassword.error = "Ingrese una contraseña"
                return@setOnClickListener
            }
            signIn(correo, contrasena)
        }

        binding.tvRegistrar.setOnClickListener {
            try {
                val intent = Intent(this, RegistrarActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
                    // Guardar el estado de la sesión
                    //al iniciar sesion y cerrar la app no vuelva a pedir credenciales.
                    val sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    // Redirigir a la actividad principal
                    val intent = Intent(this, ActivityNavigator::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "El correo o la contraseña son incorrectos, pruebe nuevamente", Toast.LENGTH_LONG).show()
                }
            }
    }
}
