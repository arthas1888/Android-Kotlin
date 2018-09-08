package co.edu.aulamatriz.netapplication.Utils

import android.net.Uri
import co.edu.aulamatriz.netapplication.databases.DBHelper
import co.edu.aulamatriz.netapplication.providers.ExampleProvider

class Constantes {

    companion object {

        val TABLE_NAME_STUDENT = "Student"

        //URIS
        val SONG_URI = Uri.parse("content://${ExampleProvider.AUTHORITY}/${DBHelper.TABLE_1}")
        val STUDENT_URI = Uri.parse("content://${ExampleProvider.AUTHORITY}/${TABLE_NAME_STUDENT}")


    }
}