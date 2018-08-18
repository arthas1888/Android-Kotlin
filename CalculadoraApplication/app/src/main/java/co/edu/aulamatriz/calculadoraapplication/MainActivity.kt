package co.edu.aulamatriz.calculadoraapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //fab = findViewById(R.id.fab) as FloatingActionButton

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

//        btnSuma.setOnClickListener{
//            Log.d(TAG, "setOnClickListener btnsuma")
//        }
//        btnSuma.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "setOnClickListener btnsuma")
    }

    @SuppressLint("SetTextI18n")
    fun procesar(view: View?) {

        val strNumber1 = etNumber1.text.toString()
        val strNumber2 = etNumber2.text.toString()
        if (validate(strNumber1, strNumber2)) {

            val num1 = strNumber1.toDouble()
            val num2 = strNumber2.toDouble()
            var res = 0.0
            when (view!!.id) {
                R.id.btnSuma -> {
                    res = num1.plus(num2) // num1 + num2
                }
                R.id.btnResta -> {
                    res = num1.minus(num2) // num1 - num2
                }
                R.id.btnMultiplicar -> {
                    res = num1.times(num2) // num1 * num2
                }
                R.id.btnDividir -> {
                    res = num1.div(num2) // num1 / num2
                }
            }
            Log.w(TAG, "res: $res")
            textViewRes.text = "Resultado: $res"
        }

    }

    private fun validate(strNumber1: String, strNumber2: String): Boolean {
        var flag = true
        if (strNumber1.isEmpty()) {
            etNumber1.setError(getText(R.string.required_field))
            etNumber1.requestFocus()
            flag = false
        } else if (strNumber2.isEmpty()) {
            etNumber2.setError(getText(R.string.required_field))
            etNumber2.requestFocus()
            flag = false
        }
        return flag
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
