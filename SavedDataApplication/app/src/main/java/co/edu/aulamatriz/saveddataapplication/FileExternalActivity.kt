package co.edu.aulamatriz.saveddataapplication

import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import java.io.*

class FileExternalActivity : AppCompatActivity() {

    var externalFile: File? = null
    val EXTERNAL_FILE_NAME = "test.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_external)

        externalFile = File(
                Environment.getExternalStorageDirectory().absolutePath
                        + File.separator + "/" + EXTERNAL_FILE_NAME)
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
        ) {
            guardar()
            leer()
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    999
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 999) {
            if (permissions[0].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                guardar()
                leer()
            } else {
                Toast.makeText(getBaseContext(),
                        "permisos no concedidos", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun guardar() {

        GuardarAsynTask(this)
                .execute(EXTERNAL_FILE_NAME)

    }

    private fun leer() {

        val fReader = FileReader(externalFile)
        val bReader = BufferedReader(fReader)
        /* Reading the contents of the file */
        val text = bReader.use(BufferedReader::readText)
        Toast.makeText(getBaseContext(),
                text, Toast.LENGTH_LONG).show()
    }


    class GuardarAsynTask : AsyncTask<String, Void, Boolean> {

        private var mContext: FileExternalActivity? = null

        constructor(context: FileExternalActivity) {
            mContext = context
        }

        override fun doInBackground(vararg params: String?): Boolean {

            val externalFile = File(
                    Environment.getExternalStorageDirectory().absolutePath
                            + File.separator + "/" + params[0])
            //val dos = params[1]
            return guardar(externalFile)
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                Toast.makeText(mContext,
                        "Datos guardados externamente",
                        Toast.LENGTH_LONG).show()
                mContext!!.leer()
            }
        }

        private fun guardar(externalFile: File): Boolean {
            val writer: FileWriter
            try {


                writer = FileWriter(externalFile)
                /** Saving the contents to the fle*/
                writer.write("texto de prueba")
                /** Closing the writer object */
                writer.close()
                return true
            } catch (e: IOException) {
                return false
            }
        }

    }
}
