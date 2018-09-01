package co.edu.aulamatriz.listaapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import co.edu.aulamatriz.listaapplication.adapters.ImageAdapter
import co.edu.aulamatriz.listaapplication.models.Country

import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast


class FormActivity : AppCompatActivity() {

    var flag: Int? = null
    var adapter = ImageAdapter(this)
    var country: Country? = null
    var pos: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>,
                                        selectedItemView: View,
                                        position: Int, id: Long) {
                flag = adapter.getItem(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }

        val extras = intent.extras
        if (extras != null) {
            country = extras.getSerializable("object") as Country
            pos = extras.getInt("pos")
            nameET.setText(country!!.name)
            cityET.setText(country!!.city)
            if (country!!.flag!! > 0)
                spinner.setSelection(adapter.getPos(country!!.flag!!)!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    public fun onCancel(view: View) {
        onBackPressed()
    }

    public fun submit(view: View) {

        val name = nameET.text.toString()
        val city = cityET.text.toString()
        if (!name.isEmpty() && !city.isEmpty()) {

            Log.w("Form", "flag $flag")
            val intent = Intent()
            intent.putExtra("pos", pos)
            intent.putExtra("country", Country(name, "", city, flag!!))

            if (pos >= 0) setResult(UPDATE, intent)
            else setResult(CREATE, intent)
            Toast.makeText(this, "Pais creado", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    companion object {
        val CREATE = 0
        val UPDATE = 1
    }
}
