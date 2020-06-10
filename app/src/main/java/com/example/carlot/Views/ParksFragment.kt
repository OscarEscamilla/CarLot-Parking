package com.example.carlot.Views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.Interfaces.ClickListener
import com.example.carlot.Models.Parks
import com.example.carlot.R
import com.example.carlot.Views.Adapters.ParksAdapter
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_2.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var vista: View? = null
    var recycler_parks: RecyclerView? = null
    var adapter: RecyclerView.Adapter<*>? = null
    var layout_manager: RecyclerView.LayoutManager? = null

    var toolbar: Toolbar? = null

    var parks: ArrayList<Parks>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        setHasOptionsMenu(true);
        // valida si los datos del web service ya fueron traidos para evitar peticiones innecesarias
        validaPersistenceDatosParks()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_parks, container, false)

        recycler_parks = vista!!.findViewById(R.id.rv_parks)
        recycler_parks?.hasFixedSize(true)
        // setter de layout manager a el recycler view
        layout_manager = GridLayoutManager(context, 2)
        recycler_parks?.layoutManager = layout_manager

        toolbar = vista?.findViewById(R.id.toolbar_fragment_parks)
        //toolbar.inflateMenu()

        (activity as AppCompatActivity).setSupportActionBar(toolbar)



        return vista
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_park, menu)


        val item_search = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = item_search.actionView as SearchView

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("query_submit", query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("query_text", newText)
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_map -> {
                var i = Intent(context, MapActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun validaPersistenceDatosParks(){
        val TAG = "validate-request-ws"
        Log.e(TAG, "park value is " + this.parks)
        if (this.parks == null){
            Log.e(TAG, "entro al if para realizar la peticion")
            getParks()
        } else {
            Log.e(TAG, "entro al else para NO realizar la peticion")
            setAdapterRecycler()
        }
    }
    private fun setAdapterRecycler(){
        // setter de el adapter a la lista
        adapter = ParksAdapter(this.parks!!, context!!, object: ClickListener{
            override fun onClick(v: View, i: Int) {
                // funcion que lanza intent a activity detalle
                lanzarDetalle(parks!!.get(i))
            }

        })
        recycler_parks?.adapter = adapter
    }


    fun getParks(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://carlotapinode.herokuapp.com/get_all_parks"
        parks = ArrayList<Parks>()
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var data = JSONArray(response)

                for (i in 0..data.length() - 1){
                    var item = JSONObject(data.get(i).toString())

                    parks!!.add(Parks(
                        item.get("id") as Int,
                        item.get("nombre_park") as String,
                        item.get("calle") as String,
                        item.get("colonia") as String,
                        item.get("numero_ext") as Int,
                        item.get("stock") as Int,
                        item.get("dia_ini") as String,
                        item.get("dia_fin") as String,
                        item.get("hora_apertura") as String,
                        item.get("hora_cierre") as String,
                        item.get("descripcion") as String,
                        item.get("id_person") as Int,
                        item.get("longitud") as Int,
                        item.get("latitud") as Int,
                        item.get("image") as String,
                        item.get("tarifa")  as String
                    ))

                }

                setAdapterRecycler()
            },
            Response.ErrorListener {
                Log.i("eRROR", "ERROR")
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun lanzarDetalle(park: Parks){
        var i = Intent(context, DetalleParkActivity::class.java)
        i.putExtra("id", park.id)
        i.putExtra("name", park.name)
        i.putExtra("calle", park.calle)
        i.putExtra("colonia", park.colonia)
        i.putExtra("numero_ext", park.numero_ext)
        i.putExtra("stock", park.stock)
        i.putExtra("dia_ini", park.dia_ini)
        i.putExtra("dia_fin", park.dia_fin)
        i.putExtra("hora_apertura", park.hora_apertura)
        i.putExtra("hora_cierre", park.hora_cierre)
        i.putExtra("descripcion", park.descripcion)
        i.putExtra("id_person", park.id_person)
        i.putExtra("longitud", park.longitud)
        i.putExtra("latitud", park.latitud)
        i.putExtra("image", park.image)
        i.putExtra("tarifa", park.tarifa)
        startActivity(i)
        Toast.makeText(context, park.name , Toast.LENGTH_SHORT).show()

    }

    companion object {
        /**
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}



private fun MenuInflater.inflate(menuToolbarPark: Int) {

}


private fun RecyclerView.hasFixedSize(b: Boolean) {

}


