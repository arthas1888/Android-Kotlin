package co.edu.aulamatriz.listaapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import co.edu.aulamatriz.listaapplication.adapters.CountryRecyclerAdapter
import co.edu.aulamatriz.listaapplication.models.Country

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), CountryAdapterListener {


    var list = ArrayList<Country>()
    var adapter: CountryRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            //startActivity(Intent(this, FormActivity::class.java))
            startActivityForResult(
                    Intent(this, FormActivity::class.java), 0)
        }


        //list.add(Country("Colombia", "", "Bogota"))

        adapter = CountryRecyclerAdapter(list, this)
        recycler.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val country = data.getSerializableExtra("country") as Country

            when (resultCode) {
                FormActivity.CREATE -> {

                    list.add(country)
                    adapter!!.notifyItemInserted(list.size - 1)
                }
                FormActivity.UPDATE -> {
                    val pos = data.getIntExtra("pos", -1)
                    Log.w("Main", "pos: $pos")
                    list.set(pos, country)
                    adapter!!.notifyItemChanged(pos)
                    //adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDeleteCountryClick(item: Country, pos: Int) {
        Log.d("MainActivity", "onDeleteCountryClick $pos")
        list.removeAt(pos)
        adapter!!.notifyItemRemoved(pos)
    }

    override fun onUpdateCountryClick(item: Country, pos: Int) {
        Log.d("MainActivity", "onUpdateCountryClick")
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("object", item)
        intent.putExtra("pos", pos)
        startActivityForResult(intent, 0)
    }
}
