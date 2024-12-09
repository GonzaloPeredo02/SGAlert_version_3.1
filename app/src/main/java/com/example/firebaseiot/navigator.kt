package com.example.firebaseiot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseiot.databinding.ActivityNavigatorBinding
import com.example.firebaseiot.views.contactoFragment
import com.example.firebaseiot.views.cuentaFragment
import com.example.firebaseiot.views.inicioFragment
import com.example.firebaseiot.views.lecturaFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ActivityNavigator : AppCompatActivity() {

    //configuracion de  viewbinding
    private lateinit var binding: ActivityNavigatorBinding
    //configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityNavigatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializar firebase con auth
        auth = Firebase.auth

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //cargar fragment por defecto
        if(savedInstanceState== null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                inicioFragment()).commit()

        }
        //cargar fragmen de acuerdo a que objeto se le hace click en el bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        inicioFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        contactoFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        lecturaFragment()).commit()
                    true
                }
                R.id.item_4 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        cuentaFragment()).commit()
                    true
                }

                else -> {false}
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.item_1 -> {
                    //se agregan toast para reconocer que la vista ya seleccionada y no la recargue
                    Toast.makeText(this, "Vista 1 ya está seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_2 -> {
                    Toast.makeText(this, "Vista 2 ya está seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_3 -> {
                    Toast.makeText(this, "Vista 3 ya está seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_4 -> {
                    Toast.makeText(this, "Vista 4 ya está seleccionada", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }


    }

}


