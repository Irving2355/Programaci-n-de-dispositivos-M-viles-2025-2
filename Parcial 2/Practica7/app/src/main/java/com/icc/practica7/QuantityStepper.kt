package com.icc.practica7

import android.R.attr.orientation
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout.HORIZONTAL
import com.icc.practica7.databinding.ViewQuantityStepperBinding

class QuantityStepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deafStyle: Int = 0
) {
    interface OnQuantityChangedListener{fun onChanged(qty: Int)}

    private lateinit var binding: ViewQuantityStepperBinding

    private var min = 1
    private var max = 99
    private var step = 1
    private var qty = 1
    private var listener: OnQuantityChangedListener? = null

    init{
        orientation = HORIZONTAL

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,
                R.styleable.QuantityStepper)
            min = ta.getInt(R.styleable.QuantityStepper_qs_min,
                1)
            max = ta.getInt(R.styleable.QuantityStepper_qs_max,
                99)
            step = ta.getInt(R.styleable.QuantityStepper_qs_step,
                1)
            qty = ta.getInt(R.styleable.QuantityStepper_qs_initial,
                min)
            ta.recycle()
        }

        binding.etQuantity.setText(qty.toString())
        assignEvents()
    }

    private fun assignEvents(){
        binding.btnMinus.setOnClickListener { changedBy(-step) }
        binding.btnPlus.setOnClickListener { changedBy(step) }

        binding.etQuantity.setOnFocusChangeListener{ _,hasFocus ->
            if(!hasFocus){
                val newVal = binding.etQuantity.text.toString()
                    .toIntOrNull()?:qty
                setQuantity(newVal, fromUser = true)
            }
        }

        binding.btnMinus.setOnLongClickListener {
            setQuantity(min, fromUser = true)
            true
        }

        binding.btnPlus.setOnLongClickListener {
            setQuantity(max, fromUser = true)
            true
        }
    }


}