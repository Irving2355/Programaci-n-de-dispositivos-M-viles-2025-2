package com.icc.practica7

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.icc.practica7.databinding.ViewQuantityStepperBinding

class QuantityStepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deafStyle: Int = 0
) : LinearLayout(context,attrs,deafStyle){
    interface OnQuantityChangedListener{fun onChanged(qty: Int)}

    interface OnTotalChangedListener{fun onTotalChanged(total: Double)}

    //private lateinit var binding: ViewQuantityStepperBinding
    private var binding: ViewQuantityStepperBinding =
        ViewQuantityStepperBinding.inflate(LayoutInflater.from(
            context),this)

    private var min = 1
    private var max = 99
    private var step = 1
    private var qty = 1
    private var unitPrice = 0.0

    private var autoRepeatEnebled = true
    private var vibrateOnLimit = true

    //private var listener: OnQuantityChangedListener? = null

    //listeners externos
    private var onQtyChanged: OnQuantityChangedListener? = null
    private var onTotalChanged: OnTotalChangedListener? = null

    //estetica
    private var buttonSizePx = 0
    private var tintColor = 0
    private var cornerRadius = 0f

    //Handler para auto-repeat
    private val handler = Handler(Looper.getMainLooper())
    private var repeating = false

    init{
        orientation = HORIZONTAL

        //leer atributios del xml/tema/estilos
        val a = context.obtainStyledAttributes(attrs,R.styleable.QuantityStepper,
            deafStyle,0)
        min = a.getInt(R.styleable.QuantityStepper_qs_min,1)
        max = a.getInt(R.styleable.QuantityStepper_qs_max,99)
        step = a.getInt(R.styleable.QuantityStepper_qs_step,1)
        qty = a.getInt(R.styleable.QuantityStepper_qs_initial,min)
        unitPrice = a.getFloat(R.styleable.QuantityStepper_qs_unitPrice,
            0f).toDouble()

        val iconMinus = a.getResourceId(R.styleable.QuantityStepper_qs_buttonIconMinus,
            R.drawable.ic_remove_24)

        val iconPlus = a.getResourceId(R.styleable.QuantityStepper_qs_buttonIconPlus,
            R.drawable.ic_add_24)

        tintColor = a.getColor(R.styleable.QuantityStepper_qs_tint,
            ContextCompat.getColor(context,R.color.teal_700))

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

    private fun changedBy(delta: Int){
        setQuantity(qty + delta, fromUser = true)
    }

    fun setOnQuantityChangedListener(l: OnQuantityChangedListener?){
        listener = l
    }

    fun getQuantity(): Int = qty

    fun setQuantity(value: Int, fromUser: Boolean = false){
        val clamped = value.coerceIn(min,max)
        val changed = (clamped != qty)
        qty = clamped
        binding.etQuantity.setText(qty.toString())

        if(fromUser && value != clamped){
            Toast.makeText(context, "Limite $min , $max",
                Toast.LENGTH_SHORT).show()
        }
        if(changed) listener?.onChanged(qty)
    }
}