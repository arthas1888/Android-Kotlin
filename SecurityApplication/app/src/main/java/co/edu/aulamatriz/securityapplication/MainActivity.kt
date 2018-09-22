package co.edu.aulamatriz.securityapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contextShare = createPackageContext(
                "co.edu.aulamatriz.permissionapplication",
                Context.CONTEXT_IGNORE_SECURITY
        )
        val id: Int = 0x7f0d0028
        val stringShare = contextShare.getString(id)
        Toast.makeText(this,
                "dato compartido $stringShare",
                Toast.LENGTH_LONG).show()
    }
}
