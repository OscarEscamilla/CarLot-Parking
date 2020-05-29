package com.example.carlot.Views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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


        getParks()

        return vista
    }

    fun getParks(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://carlotapinode.herokuapp.com/get_all_parks"
        var parks = ArrayList<Parks>()
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var data = JSONArray(response)

                for (i in 0..data.length() - 1){
                    var item = JSONObject(data.get(i).toString())

                    parks.add(Parks(
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
                // setter de el adapter a la lista
                adapter = ParksAdapter(parks, context!!)
                recycler_parks?.adapter = adapter

            },
            Response.ErrorListener {
                Log.i("eRROR", "ERROR")
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
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

private fun RecyclerView.hasFixedSize(b: Boolean) {

}


