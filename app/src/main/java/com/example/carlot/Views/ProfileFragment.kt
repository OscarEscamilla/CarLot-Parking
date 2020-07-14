package com.example.carlot.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carlot.Models.Cars
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot
import com.example.carlot.Utils.SessionManager
import com.example.carlot.Views.Adapters.CarsAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_3.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    // vista
    private var vista: View? = null
    var toolbar: Toolbar? = null
    var img_user: ImageView? = null
    var pb_cars: ProgressBar? = null
    var rv_cars: RecyclerView? = null
    var tv_email: TextView? = null
    var tv_nombre_completo: TextView? = null
    var gson: Gson? = null
    lateinit var sharedPreferences: SharedPreferences
    // api service
    var serviceCarLot: ServiceCarlot? = null
    var itemsCars = ArrayList<Cars>()
    var adapterCars: CarsAdapter? = null
    var sessionManager: SessionManager? = null
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true);


        // init session manager
        sharedPreferences = this.activity!!.getSharedPreferences("Carlot", Context.MODE_PRIVATE)
        sessionManager = SessionManager(sharedPreferences, context!!)
        user = sessionManager!!.getSession() // retorna objeto de la clase usuario



        // init variables
        serviceCarLot = RetrofitClient().getClientService()
        gson = Gson()
        // end init variables

        initDataOnViewComponents()
        getCarsRetrofit()





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_profile, container, false)
        initView()
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun initDataOnViewComponents(){
        tv_nombre_completo?.text = "${user?.nombre} ${user?.apellido}"
        tv_email?.text = user?.correo
    }



    fun initView(){

        toolbar = vista!!.findViewById(R.id.toolbar_profile);
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        img_user = vista!!.findViewById(R.id.img_user)
        pb_cars = vista!!.findViewById(R.id.pb_cars)
        tv_nombre_completo = vista!!.findViewById(R.id.tv_nombre_completo)
        tv_email = vista!!.findViewById(R.id.tv_email)


        Picasso.get()
            .load(user?.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img_user);

        rv_cars = vista!!.findViewById(R.id.rv_cars)
        // set layut manager to ReyclerView
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_cars?.layoutManager = linearLayoutManager
        rv_cars?.itemAnimator = DefaultItemAnimator()



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.config ->{
                Toast.makeText(context,"config",Toast.LENGTH_LONG).show()
            }
            R.id.logout ->{
                sessionManager?.deleteSession()
                activity?.finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun getCarsRetrofit() {
        showProgresBar()
        var mCall = serviceCarLot?.getCars(user?.id.toString())
        mCall?.enqueue(object : Callback<List<Cars>>{
            override fun onResponse(
                call: Call<List<Cars>?>?,
                response: retrofit2.Response<List<Cars>?>?
            ) {
                if (response!!.isSuccessful()){
                    var data = JSONArray(gson?.toJson(response.body()))
                    for (i in 0..data.length() -1){
                        var user = gson?.fromJson(data.get(i).toString(), Cars::class.java)
                        itemsCars.add(user!!)
                    }
                    // init adapter
                    adapterCars = CarsAdapter( itemsCars!! , context!!)
                    rv_cars?.adapter = adapterCars
                    hideProgresBar()
                }
            }
            override fun onFailure(call: Call<List<Cars>?>, t: Throwable?) {
                if (call.isCanceled()) {

                }
            }
        })
    }

    fun showProgresBar(){
        pb_cars?.visibility = View.VISIBLE
    }

    fun hideProgresBar(){
        pb_cars?.visibility = View.GONE
    }




}
