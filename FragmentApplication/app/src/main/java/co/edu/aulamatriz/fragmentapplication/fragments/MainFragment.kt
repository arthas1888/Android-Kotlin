package co.edu.aulamatriz.fragmentapplication.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.edu.aulamatriz.fragmentapplication.R
import java.io.IOException

class MainFragment : Fragment() {

    var listener: OnMainFragmentListener? = null

    companion object {
        fun createInstance(param1: String): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString("param1", param1)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnMainFragmentListener) {
            listener = context
        } else {
            throw MainFragmentException()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var param1 = ""
        if (arguments != null)
            param1 = arguments!!.getString("param1")
        Toast.makeText(activity, param1, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main_fragment,
                container, false
        )

        if (listener != null) {
            listener!!.onClick("este es un dato desde el fragmento")
        }

        return view
    }

    fun fromActivity(param: String) {
        Toast.makeText(activity, param, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnMainFragmentListener {
        fun onClick(param: String)
    }

    class MainFragmentException
        : Exception("Por favor implementar la interfaz OnMainFragmentListener en la actividad")
}