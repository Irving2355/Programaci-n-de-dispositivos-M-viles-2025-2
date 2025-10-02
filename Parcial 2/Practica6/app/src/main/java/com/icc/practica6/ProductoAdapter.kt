package com.icc.practica6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icc.practica6.databinding.ItemproductoBinding

class ProductoAdapter(
    private val items: MutableList<Producto>
) : RecyclerView.Adapter<ProductoAdapter.VH>(){
    inner class VH(val b: ItemproductoBinding):
        RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        val binding = ItemproductoBinding.inflate(
            inf, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.b.txtNombre.text = p.nombre
        holder.b.txtPrecio.text = "$" + String.format("%.2f", p.precio)

        //cargar imagen
        if(!p.imagenUrl.isNullOrBlank()){
            Glide.with(holder.itemView)
                .load(p.imagenUrl)
                .centerCrop()
                .into(holder.b.imgProducto)
        }else{
            holder.b.imgProducto.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(nuevos: List<Producto>){
        items.clear()
        items.addAll(nuevos)
        notifyDataSetChanged()
    }

}