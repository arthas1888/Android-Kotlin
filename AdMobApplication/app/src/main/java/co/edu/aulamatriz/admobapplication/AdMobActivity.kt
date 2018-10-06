package co.edu.aulamatriz.admobapplication

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_ad_mob.*


class AdMobActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_mob)

        // Load an ad into the AdMob banner view.
        val adRequest = AdRequest.Builder()
                //.setRequestAgent("android_studio:ad_template")
                .addTestDevice("BFA9D7876F9CAEC298BB73FB4DAD947E")
                .build()
        adView.loadAd(adRequest)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_ad_mob, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }

}
