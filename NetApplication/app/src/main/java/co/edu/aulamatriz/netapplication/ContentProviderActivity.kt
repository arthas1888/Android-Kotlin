package co.edu.aulamatriz.netapplication

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import co.edu.aulamatriz.netapplication.Utils.Constantes
import co.edu.aulamatriz.netapplication.databases.DBHelper
import co.edu.aulamatriz.netapplication.models.Song
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.content_main.*

class ContentProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
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

                    val songs = Gson().fromJson<ArrayList<Song>>(response, listType)

                    songs.forEach {
                        val cv = ContentValues()
                        cv.put(DBHelper.COLUMN_SERVER_ID, it.id)
                        cv.put(DBHelper.COLUMN_NAME, it.nombre)
                        cv.put(DBHelper.COLUMN_ALBUM, it.album)
                        cv.put(DBHelper.COLUMN_AUTOR, it.autor)
                        contentResolver.insert(Constantes.SONG_URI, cv)
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
        val listAdapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                contentResolver.query(Constantes.SONG_URI, null, null,
                        null, null),
                arrayOf(DBHelper.COLUMN_NAME, DBHelper.COLUMN_AUTOR),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
        )
        listView.adapter = listAdapter
    }
}
