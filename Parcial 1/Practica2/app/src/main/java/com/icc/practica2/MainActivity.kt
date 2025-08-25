package com.icc.practica2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var rgNivel: RadioGroup
    private lateinit var rbBasico: RadioButton
    private lateinit var rbIntermedio: RadioButton
    private lateinit var rbAvanzado: RadioButton
    private lateinit var cbKotlin: CheckBox
    private lateinit var cbJetpack: CheckBox
    private lateinit var cbFirebase: CheckBox
    private lateinit var btnVerSeleccion: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pantallas fin a fin es opcional
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rgNivel = findViewById(R.id.rgNivel)
        rbBasico = findViewById(R.id.rbBasico)
        rbIntermedio = findViewById(R.id.rbIntermedio)
        rbAvanzado = findViewById(R.id.rbAvanzado)
        cbKotlin = findViewById(R.id.cbKotlin)
        cbJetpack = findViewById(R.id.cbJetpack)
        cbFirebase = findViewById(R.id.cbFirebase)
        btnVerSeleccion = findViewById(R.id.btnVerSeleccion)
        tvResultado = findViewById(R.id.tvResultado)

        // listener Radiogroup
        rgNivel.setOnCheckedChangeListener {
            _, checkedId ->
            val nivel = when (checkedId){
                R.id.rbBasico -> "Basico"
                R.id.rbIntermedio -> "Intermedio"
                R.id.rbAvanzado -> "Avanzado"
                else -> "Ninguno"
            }
            Log.i("Nivel","Nivel cambio a $nivel")
        }
    }
}
