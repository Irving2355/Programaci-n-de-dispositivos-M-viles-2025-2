package com.icc.practica6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.icc.practica6.databinding.ActivityrecyclerBinding
import com.parse.ParseObject
import com.parse.ParseQuery

class ListRecyclerActivity: AppCompatActivity() {
    private lateinit var b: ActivityrecyclerBinding
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityrecyclerBinding.inflate(layoutInflater)
        setContentView(b.root)

        adapter = ProductoAdapter(mutableListOf())
        b.recyclerProductos.layoutManager = LinearLayoutManager(this)
        b.recyclerProductos.adapter = adapter

        cargarDesdeBack4App()
    }

    private fun cargarDesdeBack4App(){
        val query = ParseQuery.getQuery<ParseObject>("Producto")
        query.orderByAscending("nombre")
        query.findInBackground{ lista, e ->
            if(e == null && lista != null){
                val mapeo = lista.map{ po ->
                    Producto(
                        id = po.objectId,
                        nombre = po.getString("nombre")?: "Sin nombre",
                        precio = po.getNumber("precio")?.toDouble() ?: 0.0,
                        imagenUrl = po.getString("imagenUrl")
                    )
                }
                adapter.submitList(mapeo)
            }
        }
    }
}