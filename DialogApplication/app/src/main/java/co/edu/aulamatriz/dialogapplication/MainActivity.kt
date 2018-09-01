package co.edu.aulamatriz.dialogapplication

import android.app.DatePickerDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var toggle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            //launchTimeDialog()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", View.OnClickListener { view ->

                    }).show()
        }

        fab2.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
    }

    private fun launchDateDialog() {

        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)

        val dateTimePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
        { DatePicker, year, month, day ->
            Toast.makeText(this, "year: $year month: $month day: $day ", Toast.LENGTH_SHORT).show()
        },
                year, month, day)

        dateTimePicker.show()
    }

    private fun launchTimeDialog() {

        val calendar = Calendar.getInstance()
        val minute: Int = calendar.get(Calendar.MINUTE)
        val hour: Int = calendar.get(Calendar.HOUR)

        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener()
        { timePicker, hour, minute ->
            Toast.makeText(this, "hour: $hour minute: $minute", Toast.LENGTH_SHORT).show()
        },
                hour, minute, true)

        timePicker.show()
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
        when (item.itemId) {
            R.id.action_settings -> {
                toggle = !toggle
                if (toggle)
                    progressBar.visibility = View.VISIBLE
                else
                    progressBar.visibility = View.INVISIBLE
            }
            R.id.action_save -> showDialog()
            R.id.action_notification-> launchNotification()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(this)
                .setTitle("Guardar")
                .setMessage("Estas seguro de guardar cambios?")
                //.setView(R.layout.abc_action_menu_item_layout)
                .setPositiveButton("Si",
                        { dialog, which ->
                            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
                        })
                .setNegativeButton("No", null)

        builder.create().show()
    }

    private fun launchNotification(){
        var builder = NotificationCompat.Builder(this, "0")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Nuevo mensaje")
                //.setPriority(Notification.PRIORITY_HIGH)
                .setContentText("Esta es una notificacion personalizada lanzada desde mi actividad")

        val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notiManager.notify(1, builder.build())
    }
}
