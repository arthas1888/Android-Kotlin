package co.edu.aulamatriz.netapplication

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.edu.aulamatriz.netapplication.Utils.Constantes
import co.edu.aulamatriz.netapplication.adapters.CustomRecyclerAdapter
import co.edu.aulamatriz.netapplication.adapters.SongRecyclerAdapter
import co.edu.aulamatriz.netapplication.models.Song
import kotlinx.android.synthetic.main.activity_recycler_db.*


class RecyclerDBActivity : AppCompatActivity() {

    lateinit var data: ArrayList<Song>
    lateinit var adapter: CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_db)

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager = GridLayoutManager(this, 1)
        recycler.layoutManager = layoutManager
        adapter = CustomRecyclerAdapter()
        recycler.adapter = adapter
    }

    fun addStudent(view: View){
        var name = nameEditText.text.toString()
        var lastName = lastNameEditText.text.toString()

        if (!name.isEmpty()){
            var cv = ContentValues()
            cv.put("name", name)
            cv.put("lastName", lastName)
            contentResolver.insert(Constantes.STUDENT_URI, cv)
            adapter.swap(contentResolver.query(Constantes.STUDENT_URI, null,
                    null, null, null))
        }
    }
}
