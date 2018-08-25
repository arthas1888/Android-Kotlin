package co.edu.aulamatriz.listaapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import co.edu.aulamatriz.listaapplication.adapters.ImageAdapter
import co.edu.aulamatriz.listaapplication.models.Country

import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*
import android.widget.AdapterView.OnItemSelectedListener


class FormActivity : AppCompatActivity() {

    var flag: Int? = null
    var adapter = ImageAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>,
                                        selectedItemView: View,
                                        position: Int, id: Long) {
                flag = adapter.getItem(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        })
    }

    public fun onCancel(view: View) {

    }

    public fun submit(view: View) {

        var name = nameET.text.toString()
        var city = cityET.text.toString()
        if (!name.isEmpty() && !city.isEmpty()) {

            Log.w("Form", "flag $flag")
            var intent = Intent()
            intent.putExtra("country", Country(name, "", city, flag!!))
            setResult(1, intent)
            finish()
        }
    }
}
