package com.icc.practica2

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
        //enableEdgeToEdge()
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

        //listener para checkbox
        val checkListener = CompoundButton.OnCheckedChangeListener{
            button, isChecked ->
            Log.i("CheckBox","Checkbox ${button.text}: $isChecked")
        }
        cbKotlin.setOnCheckedChangeListener(checkListener)
        cbJetpack.setOnCheckedChangeListener(checkListener)
        cbFirebase.setOnCheckedChangeListener(checkListener)

        //Accion del boton
        btnVerSeleccion.setOnClickListener {
            val nivel = when (rgNivel.checkedRadioButtonId){
                R.id.rbBasico -> "Basico"
                R.id.rbIntermedio -> "Intermedio"
                R.id.rbAvanzado -> "Avanzado"
                else -> "Ninguno"
            }

            //buildList + ifEmpty
            val tecnologias = buildList {
                if(cbKotlin.isChecked) add("Kotlin")
                if(cbJetpack.isChecked) add("Jetpack")
                if(cbFirebase.isChecked) add("Firebase")
            }.joinToString { ", " }.ifEmpty { "Ninguna" }

            val texto = "Nivel: $nivel\nTecnologías: $tecnologias"
            tvResultado.text = texto
            Toast.makeText(this,"Leido",Toast.LENGTH_SHORT).show()
        }

        //Restauracion
        savedInstanceState?.let{
            state->
            rgNivel.check(state.getInt("nivelCheckedId", -1))
            cbKotlin.isChecked = state.getBoolean("cbKotlin",false)
            cbJetpack.isChecked = state.getBoolean("cbJetpack",false)
            cbFirebase.isChecked = state.getBoolean("cbFirebase",false)
            tvResultado.text = state.getString("resultado","Resultado aparecera aqui")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("nivelCkeckedId", rgNivel.checkedRadioButtonId)
        outState.getBoolean("cbKotlin", cbKotlin.isChecked)
        outState.getBoolean("cbJetpack", cbJetpack.isChecked)
        outState.getBoolean("cbFirebase", cbFirebase.isChecked)
        outState.getString("resultado",tvResultado.text.toString())
    }
}
