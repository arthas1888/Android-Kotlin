package co.edu.aulamatriz.netapplication.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.database.sqlite.SQLiteQueryBuilder
import co.edu.aulamatriz.netapplication.Utils.Constantes
import co.edu.aulamatriz.netapplication.databases.DBHelper
import co.edu.aulamatriz.netapplication.databases.ORMHelper


class ExampleProvider : ContentProvider() {
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var myDB: ORMHelper? = null

    companion object {
        val AUTHORITY = "co.edu.aulamatriz.netapplication.providers.ExampleProvider"
        val SONG = 1
        val SONG_ID = 2
        val STUDENT = 3
        val STUDENT_ID = 4
    }

    init {
        sUriMatcher.addURI(AUTHORITY, DBHelper.TABLE_1, SONG)
        sUriMatcher.addURI(AUTHORITY, "${DBHelper.TABLE_1}/#", SONG_ID)
        sUriMatcher.addURI(AUTHORITY, Constantes.TABLE_NAME_STUDENT, STUDENT)
        sUriMatcher.addURI(AUTHORITY, "${Constantes.TABLE_NAME_STUDENT}/#", STUDENT_ID)
    }

    override fun onCreate(): Boolean {
        myDB = ORMHelper()
        return true
    }


    override fun query(uri: Uri?,
                       columns: Array<out String>?,
                       selection: String?,
                       selectionArgs: Array<out String>?,
                       orderBy: String?): Cursor {

        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = DBHelper.TABLE_1

        val uriType = sUriMatcher.match(uri)

        when (uriType) {
            SONG_ID -> queryBuilder.appendWhere(DBHelper.COLUMN_ID + "="
                    + uri!!.lastPathSegment)
            SONG -> {
            }
            STUDENT_ID -> queryBuilder.appendWhere(DBHelper.COLUMN_ID + "="
                    + uri!!.lastPathSegment)
            STUDENT -> {
                queryBuilder.tables = Constantes.TABLE_NAME_STUDENT
            }
            else -> throw IllegalArgumentException("Unknown URI")
        }

        val cursor = queryBuilder.query(myDB?.readableDatabase,
                columns, selection, selectionArgs, null, null,
                orderBy)
        cursor.setNotificationUri(context.contentResolver,
                uri)
        return cursor
    }

    override fun insert(uri: Uri?, cv: ContentValues?): Uri {

        var table: String
        val uriType = sUriMatcher.match(uri)
        val sqlDB = myDB!!.writableDatabase

        when (uriType) {
            SONG -> {
                table = DBHelper.TABLE_1
            }
            STUDENT -> {
                table = Constantes.TABLE_NAME_STUDENT
            }
            else -> throw IllegalArgumentException("Unknown URI")
        }
        val id = sqlDB.insert(table, null, cv)
        context.contentResolver.notifyChange(uri, null)
        return Uri.parse(table + "/" + id)
    }


    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        throw IllegalArgumentException("Unknown URI")
    }

    override fun delete(uri: Uri?, where: String?, whereArgs: Array<out String>?): Int {
        val uriType = sUriMatcher.match(uri)
        val sqlDB = myDB!!.writableDatabase
        var selection = where
        var table: String
        when (uriType) {
            SONG -> {
                table = DBHelper.TABLE_1
            }
            STUDENT -> {
                table = Constantes.TABLE_NAME_STUDENT
            }
            STUDENT_ID -> {
                table = Constantes.TABLE_NAME_STUDENT
                selection += DBHelper.COLUMN_ID + "=" + uri!!.lastPathSegment
            }
            else -> throw IllegalArgumentException("Unknown URI")
        }
        return sqlDB.delete(table, selection, whereArgs)
    }

    override fun getType(p0: Uri?): String {
        throw IllegalArgumentException("Unknown URI")
    }

}