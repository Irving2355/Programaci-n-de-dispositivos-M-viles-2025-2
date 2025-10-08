package com.icc.practica7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var unitPrice = 49.90

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUnitPrice.text = getString(R.string.unit_price,
            unitPrice)

        binding.qs.setOnQuantityChangedListener(
            object  : QuantityStepper.OnQuantityChangedListener{
                override fun onChanged(qty: Int) {
                    updateTotal(qty)
                }
            }
        )

        updateTotal(binding.qs.getQuantity())
    }

    private fun updateTotal(qty: Int){
        val total = unitPrice * qty
        binding.tvTotal.text = getString(R.string.title_total,
            total)
    }
}