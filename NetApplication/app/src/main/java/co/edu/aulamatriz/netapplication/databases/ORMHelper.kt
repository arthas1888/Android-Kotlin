package co.edu.aulamatriz.netapplication.databases

import android.database.sqlite.SQLiteDatabase
import co.edu.aulamatriz.netapplication.App
import co.edu.aulamatriz.netapplication.models.Song
import co.edu.aulamatriz.netapplication.models.Student
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class ORMHelper  : OrmLiteSqliteOpenHelper(App.instance,
        "bank.db", null, 2) {

    override fun onCreate(database: SQLiteDatabase?,
                          connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, Song::class.java)
        TableUtils.createTableIfNotExists(connectionSource, Student::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?,
                           connectionSource: ConnectionSource?,
                           oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<Student, Any>(connectionSource, Student::class.java, true)
        TableUtils.dropTable<Song, Any>(connectionSource, Song::class.java, true)
        onCreate(database, connectionSource)
    }

}