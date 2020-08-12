package com.example.carlot.Views.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carlot.Models.Cars
import com.example.carlot.Models.HistorialBody
import com.example.carlot.R

class HistorialAdapter(items: ArrayList<HistorialBody>, context: Context): RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    var items: ArrayList<HistorialBody>
    var items_filtered: ArrayList<HistorialBody>
    var context: Context


    init {
        this.items = items
        this.items_filtered = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.item_historial, parent,false)
        val viewHolder = ViewHolder(vista)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = items.get(position)

        holder.tv_entrada?.text = item.entrada
//        holder.tv_marca?.text = "Marca: ${item.marca}"
//        holder.tv_matricula?.text = "Placas: ${item.matricula}"

    }


    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
        val v = vista
        var tv_entrada: TextView? = null
//        var tv_color: TextView? = null
//
//        var tv_marca: TextView? = null
        init {
              this.tv_entrada = v.findViewById(R.id.tv_fecha)
//            this.tv_marca = v.findViewById(R.id.tv_marca)
//            this.tv_matricula = v.findViewById(R.id.tv_matricula)
        }

    }
}