package co.edu.aulamatriz.pizzaapplication

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

            calcularPrecio()
        }
    }

    private fun calcularPrecio() {

        var price = 0
        when {
            radioGroup.radioButton.isChecked -> price += 7000
            radioGroup.radioButton2.isChecked -> price += 12000
            radioGroup.radioButton3.isChecked -> price += 20000
        }

        if (checkBox.isChecked)
            price += 2000
        if (checkBox2.isChecked)
            price += 1000
        if (checkBox3.isChecked)
            price += 3000

        showDialogo(price)
    }

    private fun showDialogo(price: Int) {

        val dialog = AlertDialog.Builder(this)
                .setMessage("el valor de la orden es $price")
                .setPositiveButton("Aceptar", null);
        dialog.create().show()
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
