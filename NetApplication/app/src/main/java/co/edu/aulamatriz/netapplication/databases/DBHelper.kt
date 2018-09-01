package co.edu.aulamatriz.netapplication.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?)
    : SQLiteOpenHelper(context, NAME_DB, null, VERSION_DB) {

    companion object {
        val VERSION_DB = 1
        val NAME_DB = "NET1_DB"
        val TABLE_1 = "Song"
        val COLUMN_ID = "_id"
        val COLUMN_SERVER_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_ALBUM = "album"
        val COLUMN_AUTOR = "autor"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE $TABLE_1 (
            | $COLUMN_ID integer primary key autoincrement,
            | $COLUMN_SERVER_ID integer,
            | $COLUMN_NAME text,
            | $COLUMN_ALBUM text,
            | $COLUMN_AUTOR text
            |)""".trimMargin()
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}