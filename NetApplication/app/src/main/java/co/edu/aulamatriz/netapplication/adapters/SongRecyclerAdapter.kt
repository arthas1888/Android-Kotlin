package co.edu.aulamatriz.netapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.view.View.OnLongClickListener
import co.edu.aulamatriz.netapplication.R
import co.edu.aulamatriz.netapplication.models.Song


class SongRecyclerAdapter :
        RecyclerView.Adapter<SongRecyclerAdapter.ViewHolder> {

    private var myDataset: ArrayList<Song> = ArrayList()

    constructor(myDataset: ArrayList<Song>) {
        this.myDataset = myDataset
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SongRecyclerAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_song, parent, false) as View
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView!!.text = myDataset[position].nombre
        holder.cityTextView!!.text = myDataset[position].autor

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        var mView = view
        var nameTextView: TextView? = null
        var cityTextView: TextView? = null
        var editImageView: ImageView? = null

        init {
            nameTextView = view.findViewById(R.id.nombre)
            cityTextView = view.findViewById(R.id.ciudad)
            editImageView = view.findViewById(R.id.compartir)
        }

    }
}