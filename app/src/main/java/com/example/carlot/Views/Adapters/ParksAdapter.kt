package com.example.carlot.Views.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carlot.Models.Parks
import com.example.carlot.R
import com.example.carlot.Views.ParksFragment
import com.squareup.picasso.Picasso

class ParksAdapter(items: ArrayList<Parks>, contexto: Context): RecyclerView.Adapter<ParksAdapter.ViewHolder>() {

    var items: ArrayList<Parks>
    var contexto: Context

    init {
        this.items = items
        this.contexto = contexto
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParksAdapter.ViewHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_park, parent, false)
        var view_holder =  ViewHolder(vista)
        return view_holder
    }

    override fun getItemCount(): Int {
        return  items.count()
    }

    override fun onBindViewHolder(holder: ParksAdapter.ViewHolder, position: Int) {

        // binding de dsatos del aitem a los elementos de la vista
        var item = items.get(position)
        holder.tv_name?.text = item.name

        Picasso.get()
            .load(item.image)
            .placeholder(R.drawable.car)
            .error(R.drawable.car)
            .into(holder.img_park);
    }

    // clas holder
    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var v = vista
        var tv_name: TextView? = null
        var img_park: ImageView? = null

        init {
            tv_name = v.findViewById(R.id.tv_name_park)
            img_park = v.findViewById(R.id.img_park)
        }

    }
}