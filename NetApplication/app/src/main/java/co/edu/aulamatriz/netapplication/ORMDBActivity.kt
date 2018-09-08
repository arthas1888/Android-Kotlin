package co.edu.aulamatriz.netapplication

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SimpleCursorAdapter
import co.edu.aulamatriz.netapplication.Utils.Constantes
import co.edu.aulamatriz.netapplication.databases.DBHelper
import kotlinx.android.synthetic.main.activity_ormdb.*

class ORMDBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ormdb)
    }

    fun createStudent(view: View){
        var name = nameEditText.text.toString()
        var lastName = lastNameEditText.text.toString()

        if (!name.isEmpty()){
            var cv = ContentValues()
            cv.put("name", name)
            cv.put("lastName", lastName)
            contentResolver.insert(Constantes.STUDENT_URI, cv)
            updateList()
        }
    }

    fun updateList(){
        val listAdapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                contentResolver.query(Constantes.STUDENT_URI, null, null,
                        null, null),
                arrayOf("name", "lastName"),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
        )
        listView.adapter = listAdapter
    }
}
