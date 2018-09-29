package co.edu.aulamatriz.fragmentapplication

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import co.edu.aulamatriz.fragmentapplication.fragments.BlankFragment
import co.edu.aulamatriz.fragmentapplication.fragments.ItemFragment
import co.edu.aulamatriz.fragmentapplication.fragments.MainFragment
import co.edu.aulamatriz.fragmentapplication.fragments.dummy.DummyContent

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainFragment.OnMainFragmentListener,
        BlankFragment.OnFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener {

    var fragment: MainFragment? = null
    var onChanged = false
    var columns = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        onChanged = true
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            (fragment)!!.fromActivity("este es un dato desde la actividad")

            if (onChanged) {
                replaceFragment(R.id.container2, ItemFragment.newInstance(columns))
                columns++
            } else
                replaceFragment(R.id.container2, BlankFragment())
            onChanged = !onChanged
        }
        fragment = MainFragment.createInstance("dato1")
        replaceFragment(R.id.container, fragment!!)
        replaceFragment(R.id.container2, BlankFragment())
    }

    private fun replaceFragment(id: Int, fragment: Fragment) {

        supportFragmentManager.beginTransaction()
                .replace(id, fragment)
                .commit()

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

    override fun onClick(param: String) {
        Toast.makeText(this, param, Toast.LENGTH_SHORT).show()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {

    }
}
