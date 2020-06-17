package com.example.carlot.Views

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.carlot.R
import com.squareup.picasso.Picasso

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true);
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



    fun initView(){

        toolbar = vista!!.findViewById(R.id.toolbar_profile);

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        img_user = vista!!.findViewById(R.id.img_user)

        Picasso.get()
            .load("https://is2-ssl.mzstatic.com/image/thumb/Music123/v4/4c/b4/a8/4cb4a822-8d40-8a5d-39c0-7388eff88e80/pr_source.png/800x800bb.jpeg")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img_user);



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
                Toast.makeText(context,"logout",Toast.LENGTH_LONG).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
