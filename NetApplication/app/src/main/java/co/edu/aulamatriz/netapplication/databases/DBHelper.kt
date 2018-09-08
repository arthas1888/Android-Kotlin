package co.edu.aulamatriz.netapplication.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.edu.aulamatriz.netapplication.models.Song
import com.orhanobut.logger.Logger

class DBHelper(context: Context?)
    : SQLiteOpenHelper(context, NAME_DB, null, VERSION_DB) {

    lateinit var _db: SQLiteDatabase

    init {
        _db = writableDatabase
    }

    companion object {
        val VERSION_DB = 2
        val NAME_DB = "NET1_DB"
        val TABLE_1 = "Song"
        val COLUMN_ID = "_id"
        val COLUMN_SERVER_ID = "id"
        val COLUMN_NAME = "nombre"
        val COLUMN_ALBUM = "album"
        val COLUMN_AUTOR = "autor"
    }

    fun readAll(): Cursor {
        //val selection = "($COLUMN_NAME like ? or $COLUMN_AUTOR = ?)"
        //val selectionArgs = arrayOf("""%$param%""", param)
        return _db.query(TABLE_1,
                null, null, null,
                null, null, COLUMN_NAME)
    }

    fun read(id: Int): Cursor {
        val selection = "($COLUMN_ID = ?)"
        val selectionArgs = arrayOf(id.toString())
        return _db.query(TABLE_1,
                null, selection, selectionArgs,
                null, null, COLUMN_NAME)

//        _db.query(true, TABLE_1, null,
//                null, null, null,
//                null, null, COLUMN_NAME)
    }

    fun add(song: Song) {

        val cv = ContentValues()
        cv.put(COLUMN_SERVER_ID, song.id)
        cv.put(COLUMN_NAME, song.nombre)
        cv.put(COLUMN_ALBUM, song.album)
        cv.put(COLUMN_AUTOR, song.autor)

        _db.insert(TABLE_1, null, cv)
    }

    fun update(song: Song, id: Int) {

        val selection = "($COLUMN_ID = ?)"
        val selectionArgs = arrayOf(id.toString())

        val cv = ContentValues()
        cv.put(COLUMN_SERVER_ID, song.id)
        cv.put(COLUMN_NAME, song.nombre)
        cv.put(COLUMN_ALBUM, song.album)
        cv.put(COLUMN_AUTOR, song.autor)

        _db.update(TABLE_1, cv, selection, selectionArgs)
    }

    fun delete(id: Int) {

        val selection = "($COLUMN_ID = ?)"
        val selectionArgs = arrayOf(id.toString())
        _db.delete(TABLE_1, selection, selectionArgs)
    }

    fun deleteAll() {
        _db.delete(TABLE_1, null, null)
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
        Logger.d("TABLE CREATED $TABLE_1");
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        val query = """DROP TABLE IF EXISTS $TABLE_1"""
        db!!.execSQL(query)

        onCreate(db)
    }
}