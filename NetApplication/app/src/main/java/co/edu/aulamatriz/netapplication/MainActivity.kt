package co.edu.aulamatriz.netapplication

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import co.edu.aulamatriz.netapplication.adapters.SongRecyclerAdapter
import co.edu.aulamatriz.netapplication.models.Song
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_main.*
import android.support.v7.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    lateinit var data: ArrayList<Song>
    lateinit var adapter: SongRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        data = ArrayList<Song>()

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager = GridLayoutManager(this, 1)
        recycler.layoutManager = layoutManager
        adapter = SongRecyclerAdapter(data)
        recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        getSongsFromAPI()
    }


    fun getSongsFromAPI() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://plataforma.visionsatelital.co:9080/song/"

// Request a string response from the provided URL.
//        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
//                Response.Listener<JSONArray> { response ->
//
//                    Log.w("getSongsFromAPI", "Response is: ${response.toString().substring(0, 500)}")
//                },
//                Response.ErrorListener {
//
//                })

        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    Log.w("getSongsFromAPI", "Response is: ${response.substring(0, 500)}")

                    val listType = object : TypeToken<ArrayList<Song>>() {}.type

                    val founderList = Gson().fromJson<ArrayList<Song>>(response, listType)
                    data.addAll(founderList)
                    adapter.notifyDataSetChanged()

                },
                Response.ErrorListener {

                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
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
}
