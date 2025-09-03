package com.icc.practica3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var first: Double? = null
    private var op: Char? = null
    private var clearOnNextDigit: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}