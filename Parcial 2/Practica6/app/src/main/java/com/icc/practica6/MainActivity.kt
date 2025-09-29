package com.icc.practica6

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica6.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    private lateinit var  b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnListView.setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }
        b.btnRecycler.setOnClickListener {
            startActivity(Intent(this, ListRecyclerActivity::class.java))
        }

    }
}
