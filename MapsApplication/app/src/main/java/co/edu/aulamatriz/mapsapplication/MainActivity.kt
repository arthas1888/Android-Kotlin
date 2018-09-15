package co.edu.aulamatriz.mapsapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.*
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mMap: GoogleMap
    var mGeoDataClient: GeoDataClient? = null
    var mPlaceDetectionClient: PlaceDetectionClient? = null
    private val TAG = "MainActivity"
    private val M_MAX_ENTRIES = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        val autocompleteFragment = fragmentManager
                .findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        val typeFilter = AutocompleteFilter.Builder()
                .setCountry("CO")
                .build();

        autocompleteFragment.setFilter(typeFilter)

        autocompleteFragment
                .setOnPlaceSelectedListener(
                        object : PlaceSelectionListener {
                            override fun onPlaceSelected(place: Place?) {

                                val newLatLng = place!!.latLng
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 16f))
                                mMap.addMarker(MarkerOptions().position(newLatLng)
                                        .title(place.address.toString()))
                            }

                            override fun onError(status: Status?) {
                            }

                        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                showCurrentPlace()
                true
            }
            R.id.action_intent -> {
                // Search for restaurants nearby
//                val gmmIntentUri = Uri.parse("geo:6.235683,-75.569154?q=restaurants")
//                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                mapIntent.setPackage("com.google.android.apps.maps")
//                startActivity(mapIntent)

                val gmmIntentUri = Uri.parse("google.navigation:q=6.235683,-75.569154")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    var locationManager: LocationManager? = null
    var locationListener: LocationListener? = null

    override fun onMapReady(gMap: GoogleMap?) {
        mMap = gMap!!
        mMap.setOnMapClickListener(touchListener)
        val latLng = LatLng(4.650062, -74.066051)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        mMap.addMarker(MarkerOptions().position(latLng).title("AulaMatriz"))

        locationManager = getSystemService(Context.LOCATION_SERVICE)
                as LocationManager
        locationListener = object : LocationListener {
            /**
             * Called when the location has changed.
             *
             *
             *  There are no restrictions on the use of the supplied Location object.
             *
             * @param location The new location, as a Location object.
             */
            override fun onLocationChanged(location: Location?) {

                if (location != null) {
                    Log.w("location current",
                            "latitude: ${location.latitude} " +
                                    "longitude: ${location!!.longitude}")
                    val newLatLng = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 16f))
                    mMap.addMarker(MarkerOptions().position(newLatLng)
                            .title(getAddress(location.latitude, location.longitude)))
                    getRequestAPI(location.latitude, location.longitude)
                }
            }

            /**
             * Called when the provider status changes. This method is called when
             * a provider is unable to fetch a location or if the provider has recently
             * become available after a period of unavailability.
             *
             * @param provider the name of the location provider associated with this
             * update.
             * @param status [LocationProvider.OUT_OF_SERVICE] if the
             * provider is out of service, and this is not expected to change in the
             * near future; [LocationProvider.TEMPORARILY_UNAVAILABLE] if
             * the provider is temporarily unavailable but is expected to be available
             * shortly; and [LocationProvider.AVAILABLE] if the
             * provider is currently available.
             * @param extras an optional Bundle which will contain provider specific
             * status variables.
             *
             *
             *  A number of common key/value pairs for the extras Bundle are listed
             * below. Providers that use any of the keys on this list must
             * provide the corresponding value as described below.
             *
             *
             *  *  satellites - the number of satellites used to derive the fix
             *
             */
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            /**
             * Called when the provider is enabled by the user.
             *
             * @param provider the name of the location provider associated with this
             * update.
             */
            override fun onProviderEnabled(provider: String?) {
            }

            /**
             * Called when the provider is disabled by the user. If requestLocationUpdates
             * is called on an already disabled provider, this method is called
             * immediately.
             *
             * @param provider the name of the location provider associated with this
             * update.
             */
            override fun onProviderDisabled(provider: String?) {
            }

        }

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    0)
        } else {

            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 50f, locationListener)
        }
    }

    fun getAddress(lat: Double, lon: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        var address = ""
        try {
            val addressList = geocoder.getFromLocation(
                    lat, lon, 1
            )
            if (addressList != null && addressList.size > 0) {
                if (addressList[0].thoroughfare != null) {
                    address += addressList[0].thoroughfare
                }
                if (addressList[0].subThoroughfare != null) {
                    address += " " + addressList[0].subThoroughfare
                }
                if (addressList[0].subLocality != null) {
                    address += " " + addressList[0].subLocality
                }
                if (addressList[0].locality != null) {
                    address += " " + addressList[0].locality
                }

                if (addressList[0].countryName != null) {
                    address += " " + addressList[0].countryName
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return address
    }

    var touchListener = object : GoogleMap.OnMapClickListener {
        override fun onMapClick(latLng: LatLng?) {
            val newLatLng = latLng!!
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 16f))
            mMap.addMarker(MarkerOptions().position(newLatLng)
                    .title(getAddress(newLatLng.latitude, newLatLng.longitude)))

        }

    }

    @SuppressLint("MissingPermission")
    private fun showCurrentPlace() {
        if (mMap == null) {
            return
        }

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        val placeResult = mPlaceDetectionClient!!.getCurrentPlace(null)
        placeResult.addOnCompleteListener(OnCompleteListener<PlaceLikelihoodBufferResponse> { task ->
            if (task.isSuccessful && task.result != null) {
                val likelyPlaces = task.result

                // Set the count, handling cases where less than 5 entries are returned.
                val count: Int
                if (likelyPlaces.count < M_MAX_ENTRIES) {
                    count = likelyPlaces.count
                } else {
                    count = M_MAX_ENTRIES
                }

                var i = 0
                val mLikelyPlaceNames = arrayOfNulls<String>(count)
                val mLikelyPlaceAddresses = arrayOfNulls<String>(count)
                val mLikelyPlaceAttributions = arrayOfNulls<String>(count)
                val mLikelyPlaceLatLngs = arrayOfNulls<LatLng>(count)

                for (placeLikelihood in likelyPlaces) {
                    // Build a list of likely places to show the user.
                    mLikelyPlaceNames[i] = placeLikelihood.place.name as String
                    mLikelyPlaceAddresses[i] = placeLikelihood.place
                            .address as String
//                    mLikelyPlaceAttributions[i] = placeLikelihood.place
//                            .attributions as String
                    mLikelyPlaceLatLngs[i] = placeLikelihood.place.latLng

                    i++
                    if (i > count - 1) {
                        break
                    }
                }

                // Release the place likelihood buffer, to avoid memory leaks.
                likelyPlaces.release()

                // Show a dialog offering the user the list of likely places, and add a
                // marker at the selected place.
                //openPlacesDialog()

                val listener = DialogInterface.OnClickListener { dialog, which ->
                    // The "which" argument contains the position of the selected item.
                    val markerLatLng = mLikelyPlaceLatLngs[which]
                    var markerSnippet = mLikelyPlaceAddresses[which]
                    if (mLikelyPlaceAttributions[which] != null) {
                        markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which]
                    }

                    // Add a marker for the selected place, with an info window
                    // showing information about that place.
                    mMap.addMarker(MarkerOptions()
                            .title(mLikelyPlaceNames[which])
                            .position(markerLatLng!!)
                            .snippet(markerSnippet))

                    // Position the map's camera at the location of the marker.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                            16f))
                }

                // Display the dialog.
                val dialog = AlertDialog.Builder(this)
                        .setTitle("Seleccione")
                        .setItems(mLikelyPlaceNames, listener)
                        .show()

            } else {
                Log.e(TAG, "Exception: %s", task.exception)
            }
        })
    }

    fun getRequestAPI(lat: Double, lon: Double) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=$lat,$lon&radius=100&type=restaurant&key=AIzaSyDfWiA0x3dNSDqr-cdByi8Y2_2h3sDbwzI"

        val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->

                    Log.w("getSongsFromAPI", "Response is: ${response.toString().substring(0, 500)}")
                    val jsonArray = response.getJSONArray("results");
                    for (i in 0..(jsonArray.length() - 1)) {
                        val jObj = jsonArray.getJSONObject(i)
                        val lugar = Lugar(jObj)

                        val markerLatLng = lugar.latLng
                        val markerSnippet = lugar.address


                        // Add a marker for the selected place, with an info window
                        // showing information about that place.
                        mMap.addMarker(MarkerOptions()
                                .title(lugar.name)
                                .position(markerLatLng!!)
                                .snippet(markerSnippet))
                    }


                },
                Response.ErrorListener {

                })

        queue.add(JsonObjectRequest)
    }
}
