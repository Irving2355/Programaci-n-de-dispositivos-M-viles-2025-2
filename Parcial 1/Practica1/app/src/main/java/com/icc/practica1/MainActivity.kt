package com.icc.practica1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private val TAG = "Practica1"
    //private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Mensaje de arranque en consola
        Log.i(TAG, "la app inicio")

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnToast = findViewById<Button>(R.id.btnToast)
        val btnLog = findViewById <Button >(R.id. btnLog )
        val btnLimpiar = findViewById <Button >(R.id. btnLimpiar )

        //btnToast listener
        btnToast.setOnClickListener {
            val nombre = etNombre.text?.toString().orEmpty()
            val mensaje = if(nombre.isBlank()) "Hola ISC 7B"
            else "Hola, $nombre"
            Toast.makeText(this,
                mensaje, Toast.LENGTH_SHORT).show()
        }

        //btn log
        btnLog.setOnClickListener {
            Log.d(TAG, "btnlog: DEBUG")
            Log.i(TAG,"btnlog: INFO")
            Log.w(TAG,"btnlog: WARNING")
            Log.e(TAG,"btnlog: ERROR")
            Log.wtf(TAG,"ola k ase")
            val nombre = etNombre.text?.toString().orEmpty()
            val mensaje = if(nombre.isBlank()) "Hola ISC 7B"
            else "Hola, $nombre"
            Log.i(TAG,mensaje)
        }

        //btnlimpiar
        btnLimpiar.setOnClickListener {
            etNombre.text?.clear()
            Toast.makeText(this,
                "Campo limpiado",
                Toast.LENGTH_SHORT).show()
            Log.i(TAG,"btnLimpair se uso")
        }
    }
}