package com.icc.practica9

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity(){
    private lateinit var tvValor: MaterialTextView
    private lateinit var btnSumar: MaterialButton
    private var valor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvValor = findViewById(R.id.tvValor)
        btnSumar = findViewById(R.id.btnSumar)

        //cargar el valor de prefs compartido con el widget
        val prefs = getSharedPreferences(ContadorWidgetProvider.PREFS,
            MODE_PRIVATE)
        valor = prefs.getInt(ContadorWidgetProvider.KEY_CONTADOR,
            0)
        tvValor.text = valor.toString()
        btnSumar.setOnClickListener {
            valor++
            tvValor.text = valor.toString()

            prefs.edit().putInt(ContadorWidgetProvider.KEY_CONTADOR,
                valor).apply()

            val intent = Intent(ContadorWidgetProvider.ACTION_APP_EVENT)
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }

    }
}