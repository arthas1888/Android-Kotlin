package co.edu.aulamatriz.netapplication.models

import java.io.Serializable

class Song: Serializable{

    //http://plataforma.visionsatelital.co:9080/song/
    var id: Int? = null
    var nombre: String? = null
    var autor: String? = null
    var album: String? = null
}