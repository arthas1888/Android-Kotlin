package co.edu.aulamatriz.listaapplication.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ImageView.ScaleType
import co.edu.aulamatriz.listaapplication.R


class ImageAdapter : BaseAdapter {

    var mContext: Context? = null
    private val images = intArrayOf(R.drawable.ic_flag_of_argentina,
            R.drawable.ic_flag_of_bolivia, R.drawable.ic_flag_of_chile,
            R.drawable.ic_flag_of_colombia,
            R.drawable.ic_flag_of_costa_rica, R.drawable.ic_flag_of_cuba,
            R.drawable.ic_flag_of_el_salvador, R.drawable.ic_flag_of_guatemala,
            R.drawable.ic_flag_of_haiti, R.drawable.ic_flag_of_honduras,
            R.drawable.ic_flag_of_mexico, R.drawable.ic_flag_of_panama,
            R.drawable.ic_flag_of_peru, R.drawable.ic_flag_of_the_dominican_republic,
            R.drawable.ic_flag_of_uruguay, R.drawable.ic_flag_of_venezuela)

    constructor(context: Context) {
        mContext = context
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        val imageView = ImageView(mContext)
        imageView.setImageResource(images[position])
        imageView.setScaleType(ImageView.ScaleType.CENTER)
        imageView.setLayoutParams(LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200))
        return imageView
    }

    override fun getItemId(pos: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(pos: Int): Int? {
        //return images.firstOrNull { x -> x == item }
        return images[pos]
    }


}