package com.example.firebaseiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri

class Emergencia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emergencia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cambiarPantalla: Button = findViewById(R.id.btnAtrasMenu)
        cambiarPantalla.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual para que no se pueda volver a ella
        }
        val buttonDialA = findViewById<Button>(R.id.llamarAmbulancia)
        buttonDialA.setOnClickListener {
            val phoneNumber = "131" //Numero al que quiero marcar. conm dial.
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(dialIntent)
        }
        val buttonDialB = findViewById<Button>(R.id.llamarBomberos)
        buttonDialB.setOnClickListener {
            val phoneNumber = "132"
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(dialIntent)
        }
        val buttonDialP = findViewById<Button>(R.id.llamarPolicia)
        buttonDialP.setOnClickListener {
            val phoneNumber = "133"
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(dialIntent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // No hacer nada para deshabilitar el botón de retroceso
    }
}
