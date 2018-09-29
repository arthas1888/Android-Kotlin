package co.edu.aulamatriz.consumerapplication

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object {
        val AUTHORITY = "co.edu.aulamatriz.dbapplication.providers.ExampleProvider"

        val JOKE_URI = Uri.parse("content://${AUTHORITY}/Joke")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cursor = contentResolver
                .query(JOKE_URI, null,
                        null,
                        null,
                        "_id")
        val adapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                arrayOf("joke",
                        "categories"),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
        )
        list.adapter = adapter


    }
}
