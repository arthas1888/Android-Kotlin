package co.edu.aulamatriz.netapplication

import android.database.Cursor
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
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import co.edu.aulamatriz.netapplication.databases.DBHelper


class MainActivity : AppCompatActivity() {

    lateinit var data: ArrayList<Song>
    lateinit var adapter: SongRecyclerAdapter
    lateinit var dbHelper: DBHelper
    lateinit var listAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getSongsFromAPI()
        dbHelper = DBHelper(this)
        dbHelper.add(Song(0, "test name", "test autor", "test album"))
        val cursor = dbHelper.readAll()
        dbHelper.deleteAll()
        updateList()
        val arrayAdapter = ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1,
                cursorToSongs(cursor)
        )

        listView.adapter = listAdapter

        fab.setOnClickListener { view ->
            val nombre = nameEditText.text.toString()
            val autor = autorEditText.text.toString()
            val album = albumEditText.text.toString()
            val song = Song(0, nombre, autor, album)
            dbHelper.add(song)
            updateList()
        }

        data = ArrayList<Song>()

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager = GridLayoutManager(this, 1)
        recycler.layoutManager = layoutManager
        adapter = SongRecyclerAdapter(data)
        recycler.adapter = adapter
    }

    private fun cursorToSongs(cursor: Cursor): ArrayList<Song> {

        val list = ArrayList<Song>()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val nombre = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME))
                val autor = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_AUTOR))
                val album = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ALBUM))
                val id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_SERVER_ID))
                val song = Song(id, nombre, autor, album)
                list.add(song)
            }
        }
        return list
    }

    override fun onStart() {
        super.onStart()
        //getSongsFromAPI()
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

                    val songs = Gson().fromJson<ArrayList<Song>>(response, listType)

                    songs.forEach {
                        dbHelper.add(it)
                    }
                    updateList()

                    //data.addAll(founderList)
                    //adapter.notifyDataSetChanged()

                },
                Response.ErrorListener {

                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun updateList(){
        listAdapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                dbHelper.readAll(),
                arrayOf(DBHelper.COLUMN_NAME, DBHelper.COLUMN_AUTOR),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
        )
        listView.adapter = listAdapter
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
