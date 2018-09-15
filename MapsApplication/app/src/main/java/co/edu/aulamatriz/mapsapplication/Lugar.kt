package co.edu.aulamatriz.mapsapplication

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

class Lugar {

    var lat: Double? = null
    var lon: Double? = null
    var name: String? = null
    var address: String? = null
    var latLng: LatLng? = null

    constructor(jObj: JSONObject){
        name = jObj.getString("name")
        address = jObj.getString("vicinity")
        val geom = jObj.getJSONObject("geometry")
        val location = geom.getJSONObject("location")
        lat = location.getDouble("lat")
        lon = location.getDouble("lng")
        latLng = LatLng(lat!!, lon!!)
    }
}