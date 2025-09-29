package com.icc.practica6

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.icc.practica6.databinding.ActivitylistviewBinding
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery

class ListViewActivity: AppCompatActivity() {
    private lateinit var b: ActivitylistviewBinding
    private val datos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitylistviewBinding.inflate(layoutInflater)
        setContentView(b.root)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<String>()
        )
        b.listViewProductos.adapter = adapter

        val query = ParseQuery.getQuery<ParseObject>("Producto")
        query.orderByAscending("nombre")
        query.findInBackground{list, e ->
            if(e == null && list != null){
                datos.clear()
                val nombres = mutableListOf<String>()
                for(po in list){
                    val p = Producto(
                        id = po.objectId,
                        nombre = po.getString("nombre") ?: "Sin nombre",
                        precio = po.getNumber("precio")?.toDouble() ?: 0.0,
                        imagenUrl = po.getString("imagenUrl")
                    )
                    datos.add(p)
                    nombres.add("${p.nombre}  \$${"%.2f".format(p.precio)}")
                }
                adapter.clear()
                adapter.addAll(nombres)
                adapter.notifyDataSetChanged()
            }
        }
    }
}