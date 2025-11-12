package com.icc.practica11

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paint = binding.paintView

        binding.spTool.onItemSelectedListener = object : AdapterView.
                OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                paint.currentTool = PaintView.Tool.values()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

        //paleta de color
        binding.btnCBlack.setOnClickListener { applyColor(Color.BLACK) }
        binding.btnCRed.setOnClickListener { applyColor(0xFFF4436.toInt()) }
        binding.btnCGreen.setOnClickListener { applyColor(0xFF4CAF50.toInt()) }
        binding.btnCBlue.setOnClickListener { applyColor(0xFF2196F3.toInt()) }
        binding.btnCYellow.setOnClickListener { applyColor(0xFFFFEB3B.toInt()) }

        //mezcaldor RGB
        val listener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?,
                                           progress: Int,
                                           fromUser: Boolean) {
                val c = Color.rgb(binding.seekR.progress,
                    binding.seekG.progress,
                    binding.seekB.progress)
                applyColor(c)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
        binding.seekR.setOnSeekBarChangeListener(listener)
        binding.seekG.setOnSeekBarChangeListener(listener)
        binding.seekB.setOnSeekBarChangeListener(listener)

        //grosor
        binding.seekStroke.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?,
                                           progress: Int,
                                           fromUser: Boolean) {
                paint.strokeWidthPx =
                    progress.coerceAtLeast(1).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //acciones
        binding.btnUndo.setOnClickListener { paint.undo() }
        binding.btnClear.setOnClickListener { paint.clearAll() }
    }

    private fun applyColor(color: Int){
        binding.paintView.currentColor = color
        binding.vColorPreview.setBackgroundColor(color)
    }
}
