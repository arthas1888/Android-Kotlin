package co.edu.aulamatriz.netapplication.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "Song")
class Song : Serializable {

    @DatabaseField(generatedId = true)
    var _id: Int? = null
    //http://plataforma.visionsatelital.co:9080/song/
    @DatabaseField
    var id: Int? = null
    @DatabaseField
    var nombre: String? = null
    @DatabaseField
    var autor: String? = null
    @DatabaseField
    var album: String? = null

    constructor()
    constructor(id: Int?, nombre: String?, autor: String?, album: String?) {
        this.id = id
        this.nombre = nombre
        this.autor = autor
        this.album = album
    }

    override fun toString(): String {
        return nombre!!
    }
}