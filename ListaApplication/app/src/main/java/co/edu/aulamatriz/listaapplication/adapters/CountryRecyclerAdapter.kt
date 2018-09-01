package co.edu.aulamatriz.listaapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.edu.aulamatriz.listaapplication.CountryAdapterListener
import co.edu.aulamatriz.listaapplication.R
import co.edu.aulamatriz.listaapplication.models.Country
import android.widget.Toast
import android.view.View.OnLongClickListener



class CountryRecyclerAdapter :
        RecyclerView.Adapter<CountryRecyclerAdapter.ViewHolder> {

    private var myDataset: ArrayList<Country> = ArrayList()
    private var mListener: CountryAdapterListener? = null

    constructor(myDataset: ArrayList<Country>, listener: CountryAdapterListener) {
        this.myDataset = myDataset
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CountryRecyclerAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_country, parent, false) as View
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView!!.text = myDataset[position].name
        holder.cityTextView!!.text = myDataset[position].city
        if (myDataset[position].flag != null)
            holder.flagImageView!!.setImageResource(myDataset[position].flag!!)
        holder.mView.setOnLongClickListener {
            if (mListener != null)
                mListener!!.onDeleteCountryClick(myDataset[position], position)

            true    // <- set to true
        }

        holder.editImageView!!.setOnClickListener {
            if (mListener != null)
                mListener!!.onUpdateCountryClick(myDataset[position], position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        var mView = view
        var nameTextView: TextView? = null
        var cityTextView: TextView? = null
        var flagImageView: ImageView? = null
        var editImageView: ImageView? = null

        init {
            nameTextView = view.findViewById(R.id.nombre)
            cityTextView = view.findViewById(R.id.ciudad)
            flagImageView = view.findViewById(R.id.imagen)
            editImageView = view.findViewById(R.id.compartir)
        }

    }
}