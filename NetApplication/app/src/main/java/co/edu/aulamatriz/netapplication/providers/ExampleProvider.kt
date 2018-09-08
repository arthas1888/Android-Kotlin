package co.edu.aulamatriz.netapplication.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.database.sqlite.SQLiteQueryBuilder
import co.edu.aulamatriz.netapplication.databases.DBHelper


class ExampleProvider : ContentProvider() {
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var myDB: DBHelper? = null

    companion object {
        val AUTHORITY = "co.edu.aulamatriz.netapplication.providers.ExampleProvider"
        val SONG = 1
        val SONG_ID = 2

    }

    init {
        myDB = DBHelper(context)
        sUriMatcher.addURI(AUTHORITY, DBHelper.TABLE_1, SONG);
        sUriMatcher.addURI(AUTHORITY, "${DBHelper.TABLE_1}/#", SONG_ID);
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
            else -> throw IllegalArgumentException("Unknown URI")
        }

        val cursor = queryBuilder.query(myDB?.readableDatabase,
                columns, selection, selectionArgs, null, null,
                orderBy)
        cursor.setNotificationUri(context.contentResolver,
                uri)
        return cursor
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri {
        throw IllegalArgumentException("Unknown URI")
    }


    override fun onCreate(): Boolean {
        throw IllegalArgumentException("Unknown URI")
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        throw IllegalArgumentException("Unknown URI")
    }

    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?): Int {
        throw IllegalArgumentException("Unknown URI")
    }

    override fun getType(p0: Uri?): String {
        throw IllegalArgumentException("Unknown URI")
    }

}