package co.edu.aulamatriz.saveddataapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_file_internal.*
import java.io.*

class FileInternalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_internal)

        crearArchivo()

        leerArchivo()
    }

    private fun leerArchivo() {

        val inStream: FileInputStream =
                openFileInput("test.txt")
        if (inStream != null) {
            val stringToReturn =
                    inStream.bufferedReader()
                            .use(BufferedReader::readText)
            textView2.setText(stringToReturn)
        }
    }

    private fun crearArchivo() {

        val oStream: FileOutputStream =
                openFileOutput("test.txt", Context.MODE_PRIVATE)
        val writer : OutputStreamWriter =
                OutputStreamWriter(oStream)
        writer.write("esto es una prueba")
        writer.flush()
        writer.close()
    }
}
