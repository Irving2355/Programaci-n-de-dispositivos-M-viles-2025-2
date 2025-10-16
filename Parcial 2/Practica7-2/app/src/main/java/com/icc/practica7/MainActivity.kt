package com.icc.practica7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //muestra el precio unitario
        val unitPrice = binding.qs.getUnitPrice()
        binding.tvUnitPrice.text = getString(R.string.unit_price,unitPrice)

        //escucha cambios de cantidad
        binding.qs.setOnQuantityChangedListener(
            object  : QuantityStepper.OnQuantityChangedListener{
                override fun onChanged(qty: Int) {
                }
            }
        )

        //escucha cambios del total
        binding.qs.setOnTotalChangedListener(object:
            QuantityStepper.OnTotalChangedListener{
                override fun onTotalChanged(total: Double){
                    binding.tvTotal.text = getString(R.string.title_total,total)
                }
            })

        //iniciar etiqueta total
        binding.qs.setQuantity(binding.qs.getQuantity())
    }
}