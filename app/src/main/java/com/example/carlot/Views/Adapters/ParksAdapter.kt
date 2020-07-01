package com.example.carlot.Views.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carlot.Interfaces.ClickListener
import com.example.carlot.Models.Parks
import com.example.carlot.R
import com.squareup.picasso.Picasso

class ParksAdapter(items: ArrayList<Parks>, contexto: Context, var listener: ClickListener): RecyclerView.Adapter<ParksAdapter.ViewHolder>() {

    var items: ArrayList<Parks>
    var items_filtered: ArrayList<Parks>
    var contexto: Context





    init {
        this.items = items
        this.items_filtered = items
        this.contexto = contexto
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParksAdapter.ViewHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_park, parent, false)
        var view_holder =  ViewHolder(vista, listener)
        return view_holder
    }

    override fun getItemCount(): Int {
        return  items.count()
    }

    override fun onBindViewHolder(holder: ParksAdapter.ViewHolder, position: Int) {

        // binding de dsatos del aitem a los elementos de la vista
        var item = items.get(position)
        holder.tv_name?.text = item.name
        holder.tv_precio?.text = "$" + item.tarifa

        Picasso.get()
            .load(item.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.img_park);


    }

    fun filtrar(texto: String) {
        // Elimina todos los datos del ArrayList que se cargan en los
        // elementos del adaptador
        items.clear()
        // Si no hay texto: agrega de nuevo los datos del ArrayList copiado
        // al ArrayList que se carga en los elementos del adaptador
        if (texto.length == 0) {
            items.addAll(items_filtered)
        } else {
            // Recorre todos los elementos que contiene el ArrayList copiado
            // y dependiendo de si estos contienen el texto ingresado por el
            // usuario los agrega de nuevo al ArrayList que se carga en los
            // elementos del adaptador.
            for (item: Parks in items_filtered) {
                if (item.name?.toLowerCase()!!.contains(texto)) {
                    items.add(item)
                }
            }
        }
        // Actualiza el adaptador para aplicar los cambios
        notifyDataSetChanged()
    }


    // class holder
    class ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener{
        var v = vista
        var tv_name: TextView? = null
        var tv_precio: TextView? = null
        var img_park: ImageView? = null

        var item_click_listener: ClickListener? = null

        init {
            this.tv_name = v.findViewById(R.id.tv_name_park)
            this.tv_precio = v.findViewById(R.id.tv_tarifa)
            this.img_park = v.findViewById(R.id.img_park)
            this.item_click_listener = listener
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.item_click_listener?.onClick(v!!, adapterPosition);
        }

    }
}
