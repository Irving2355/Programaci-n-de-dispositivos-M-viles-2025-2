package com.icc.practica7

import android.content.Context
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
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

    init {
        orientation = HORIZONTAL

        //leer atributios del xml/tema/estilos
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.QuantityStepper,
            deafStyle, 0
        )
        min = a.getInt(R.styleable.QuantityStepper_qs_min, 1)
        max = a.getInt(R.styleable.QuantityStepper_qs_max, 99)
        step = a.getInt(R.styleable.QuantityStepper_qs_step, 1)
        qty = a.getInt(R.styleable.QuantityStepper_qs_initial, min)
        unitPrice = a.getFloat(
            R.styleable.QuantityStepper_qs_unitPrice,
            0f
        ).toDouble()

        val iconMinus = a.getResourceId(
            R.styleable.QuantityStepper_qs_buttonIconMinus,
            R.drawable.ic_remove_24
        )

        val iconPlus = a.getResourceId(
            R.styleable.QuantityStepper_qs_buttonIconPlus,
            R.drawable.ic_add_24
        )

        tintColor = a.getColor(
            R.styleable.QuantityStepper_qs_tint,
            ContextCompat.getColor(context, R.color.teal_700)
        )

        cornerRadius = a.getDimension(
            R.styleable.QuantityStepper_qs_cornerRadius,
            16f
        )

        buttonSizePx = a.getDimensionPixelSize(
            R.styleable.QuantityStepper_qs_buttonSize,
            dp(40)
        )

        autoRepeatEnebled = a.getBoolean(
            R.styleable.QuantityStepper_qs_autoRepeatEnabled,
            true
        )

        vibrateOnLimit = a.getBoolean(
            R.styleable.QuantityStepper_qs_vibrateOnLimit,
            true
        )
        a.recycle()

        //aplicar conos tamaños tintes y formas
        styleButton(binding.btnMinus, iconMinus)
        styleButton(binding.btnPlus, iconPlus)

        //cantidad inicial en UI (intefaz de usuario)
        binding.etQuantity.setText(qty.toString())

        //accesibilad extra: hints y labels
        binding.etQuantity.setOnFocusChangeListener{ v, hasFocus ->
            if(!hasFocus) validateManualInput()

        }

        //clicks y long-press
        binding.btnMinus.setOnClickListener { nudge(-step) }
        binding.btnPlus.setOnClickListener { nudge(step) }

        //auto-repeat para long-press
        setupAutoRepeat(binding.btnMinus, -step)
        setupAutoRepeat(binding.btnPlus, step)

        //assignEvents()
    }

    // estetica y tamaño
    private fun styleButton(btn: MaterialButton,iconRes: Int){
        btn.icon = ContextCompat.getDrawable(context,iconRes)
        btn.layoutParams.apply {
            width = buttonSizePx
            height = buttonSizePx
        }
        btn.iconTint?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        btn.backgroundTintList = ContextCompat.getColorStateList(
            context, R.color.teal_700)
        btn.cornerRadius = cornerRadius.toInt()
    }

    private fun dp(value: Int) = (resources.displayMetrics.density * value).toInt()

    //Interacciones
    private fun nudge(delta: Int){
        val old = qty
        setQuantityInternal(qty + delta, fromUser = true)
        animateBump()
        //feedback al limite
        if(qty != old) performHapticFeedback(
            HapticFeedbackConstants.KEYBOARD_TAP)
        else if (vibrateOnLimit) performHapticFeedback(
            HapticFeedbackConstants.REJECT)
    }

    private fun validateManualInput(){
        val newVal = binding.etQuantity.text.toString().toIntOrNull()?: qty
        setQuantityInternal(newVal,fromUser = true)
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