package co.edu.aulamatriz.security2application

import android.app.backup.BackupManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

class MainActivity : AppCompatActivity() {

    private val TOP_SCORES = "TOP_SCORES"
    private var backupManager: BackupManager? = null
    private var prefs: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(BackupData.PREFS_TEST, Context.MODE_PRIVATE)
        edit = prefs?.edit()
        backupManager = BackupManager(this)

        show.isEnabled = false

        if (showData().isNotEmpty()) {
            show.isEnabled = true
        }
        save.setOnClickListener {
            saveData("save", enter_data.text.toString())
            show.isEnabled = true


            val dataFile = File(filesDir, TOP_SCORES)
            val raFile = RandomAccessFile(dataFile, "rw")
            raFile.writeChars(enter_data.text.toString())

        }
        show.setOnClickListener {
            load_data.setText(showData())
        }
        //show.isEnabled = true

    }

    fun openOther(view: View) {
        val intent = Intent()
        intent.action = "co.edu.aulamatriz.SECURITY"
        intent.addCategory("android.intent.category.DEFAULT")

        startActivity(intent)
    }

    private fun saveData(key: String, value: String) {
        edit?.putString(key, value)
        edit?.commit()
        Log.d("Test", "Calling backup...")
        backupManager?.dataChanged()
    }

    private fun showData(): String {
        return prefs?.getString("save", "").toString()
    }
}
