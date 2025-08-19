package com.icc.practica1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private val TAG = "Practica1"
    //private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Mensaje de arranque en consola
        Log.i("Ejemplo", "la app inicio")

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnToast = findViewById<Button>(R.id.btnToast)

    }
}