package co.edu.aulamatriz.saveddataapplication

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import java.io.*

class FileCacheActivity : AppCompatActivity() {

    var tempFile: File? = null
    val TEMP_FILE_NAME = "test.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_cache)

        tempFile = File(cacheDir.path + "/" + TEMP_FILE_NAME)
    }


    private fun leer() {

        val fReader = FileReader(tempFile)
        val bReader = BufferedReader(fReader)
        /* Reading the contents of the file */
        val text = bReader.use(BufferedReader::readText)
        Toast.makeText(getBaseContext(),
                text, Toast.LENGTH_LONG).show()
    }

    private fun guardar() {
        val writer: FileWriter
        writer = FileWriter(tempFile)
        /** Saving the contents to the fle*/
        writer.write("texto de prueba")
        /** Closing the writer object */
        writer.close()
        Toast.makeText(getBaseContext(),
                "File Saved in Cache", Toast.LENGTH_LONG).show()
    }
}
