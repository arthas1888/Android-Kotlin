package co.edu.aulamatriz.permissionapplication

import android.Manifest
import android.app.ListActivity
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleCursorAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE_CONTACT = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (ContextCompat.checkSelfPermission(this,
                        "android.permission.READ_CONTACTS")
                != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.READ_CONTACTS)
            ActivityCompat.requestPermissions(
                    this, permissions, REQUEST_CODE_CONTACT)
        } else {
            getContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CONTACT) {
            var index = 0
            for (permission in permissions) {
                if (permission.equals(Manifest.permission.READ_CONTACTS)) {
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        getContacts()
                        break
                    } else {
                        Toast.makeText(this, "Permisos no concedidos",
                                Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                index++
            }
        }
    }

    private fun getContacts() {

        val projection =
                arrayOf(ContactsContract.Data._ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        val uri = ContactsContract.Data.CONTENT_URI
        val cursor = contentResolver
                .query(uri, projection,
                        null,
                        null,
                        null)
        val adapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
        )
        list.adapter = adapter
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
