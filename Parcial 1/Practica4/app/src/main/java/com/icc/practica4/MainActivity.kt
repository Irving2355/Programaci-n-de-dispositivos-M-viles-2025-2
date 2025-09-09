package com.icc.practica4

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val caras = listOf(R.drawable.a1,R.drawable.a2,
        R.drawable.a3,R.drawable.a4)
    private val cuerpos = listOf(R.drawable.b1,R.drawable.b2,
        R.drawable.b3,R.drawable.b4)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adaptadores spinners
        val adpCaras = ArrayAdapter.createFromResource(
            this,R.array.caras_array,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val adpCuerpos = ArrayAdapter.createFromResource(
            this,R.array.cuerpos_array,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        //Enlazar adaptadores a los spinner
        binding.spnCaras.adapter = adpCaras
        binding.spnCuerpos.adapter = adpCuerpos

        //listener de seleccion
        binding.spnCaras.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.imgCara.setImageResource(caras[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

        binding.spnCuerpos.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.imgCuerpo.setImageResource(cuerpos[position])
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

        binding.imgCara.setImageResource(caras.first())
        binding.imgCuerpo.setImageResource((cuerpos.first()))
    }
}