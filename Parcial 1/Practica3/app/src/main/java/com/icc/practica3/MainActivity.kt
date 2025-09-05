package com.icc.practica3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica3.databinding.CalcuBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var binding: CalcuBinding

    private var first: Double? = null
    private var op: Char? = null
    private var clearOnNextDigit: Boolean = false
    private var ans: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalcuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //funciones lee el texto actual del display
        fun displayText(): String = binding.tvDisplay.text.toString()

        //escribe un texto en el display
        fun setDisplay(s: String){
            binding.tvDisplay.text = s
        }

        //Inserta un digitio en el display
        fun appendDigit(d: String){
            if(clearOnNextDigit || displayText() == "0" || displayText() == "Error"){
                setDisplay(d)
                clearOnNextDigit = false
            }else{
                setDisplay(displayText() + d)
            }
        }

        //configura operador y guarda
        fun setOperator(c: Char){
            first = displayText().toDoubleOrNull()
            op = c
            clearOnNextDigit = true
        }

        //cambia el signo del numero
        fun toggleSing(){
            val v = displayText()
            if(v == "0" || v == "Error") return
            setDisplay(if(v.startsWith("-")) v.drop(1) else "-$v")
        }

        //Agregar punto decimal
        fun addDot(){
            if(displayText() == "Error"){
                setDisplay("0.")
                clearOnNextDigit = false
                return
            }
            if(clearOnNextDigit){
                setDisplay("0.")
                clearOnNextDigit = false
                return
            }
            if(!displayText().contains(".")){
                setDisplay(displayText() + ".")
            }
        }

        //borra el ultimo caracter
        fun backspace(){
            val v = displayText()
            if(v == "Error"){
                setDisplay("0")
                clearOnNextDigit = false
                return
            }
            if(clearOnNextDigit){
                setDisplay("0")
                clearOnNextDigit = false
                return
            }
            if(v.length <= 1 || (v.length == 2 && v.startsWith("-"))){
                setDisplay("0")
            }else{
                setDisplay(v.dropLast(1))
            }
        }

        //Limpiar todo
        fun clearAll(){
            first = null
            op = null
            clearOnNextDigit = false
            setDisplay("0")
        }

        //operaciones
        fun compute(){
            val a = first ?: return
            val b = displayText().toDoubleOrNull() ?: return

            val res = when (op){
                '+' -> a + b
                '-' -> a - b
                '*' -> a * b
                '/' -> if(abs(b) < 1e-12){
                    setDisplay("Error")
                    first = null; op=null
                    clearOnNextDigit = true
                    return
                }else a / b
                else -> return
            }

            ans = res

            setDisplay(if(res % 1.0 == 0.0) res.toLong().toString() else res.toString())
            first = null
            op = null
            clearOnNextDigit = true
        }

        //Listeners
        //numeros
        binding.btn0.setOnClickListener { appendDigit("0") }
        binding.btn1.setOnClickListener { appendDigit("1") }
        binding.btn2.setOnClickListener { appendDigit("2") }
        binding.btn3.setOnClickListener { appendDigit("3") }
        binding.btn4.setOnClickListener { appendDigit("4") }
        binding.btn5.setOnClickListener { appendDigit("5") }
        binding.btn6.setOnClickListener { appendDigit("6") }
        binding.btn7.setOnClickListener { appendDigit("7") }
        binding.btn8.setOnClickListener { appendDigit("8") }
        binding.btn9.setOnClickListener { appendDigit("9") }

        //operadores
        binding.btnAdd.setOnClickListener { setOperator('+') }
        binding.btnSub.setOnClickListener { setOperator('-') }
        binding.btnMul.setOnClickListener { setOperator('*') }
        binding.btnDiv.setOnClickListener { setOperator('/') }

        //especiales
        binding.btnEq.setOnClickListener { compute() }
        binding.btnC.setOnClickListener { clearAll() }
        binding.btnBack.setOnClickListener { backspace() }
        binding.btnDot.setOnClickListener { addDot() }
        binding.btnSign.setOnClickListener { toggleSing() }

        //boton ANS
        binding.btnAns.setOnClickListener {
            val ansText = ans.toString()
            //si venimos de un calculo / error / limpeza, reemplazar
            if(clearOnNextDigit || displayText() == "0" ||displayText() == "Error"){
                setDisplay(ansText)
                clearOnNextDigit = false
            }else{
                //si ya hay contenido numerico, concatenar
                setDisplay(displayText() + ansText)
            }
        }

    }
}