package bandi.nightowl.ui.maps

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import bandi.nightowl.R
import bandi.nightowl.data.places.PlacesResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_map_fragment.*
import javax.inject.Inject


/**
 * Created by abauer on 22.11.17.
 */
class PlacesMapFragment : Fragment() {

    private var map: GoogleMap? = null
    private lateinit var placesViewModel: PlacesViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance(): PlacesMapFragment {
            return PlacesMapFragment()
        }
        private val PERMISSION_REQUEST_FINE_LOCATION: Int = 1
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.custom_map_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placesViewModel = ViewModelProviders.of(this, viewModelFactory).get(PlacesViewModel::class.java)
        observePlacesData()
        observeLoadingStatus()
        observeError()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapFragment.getMapAsync({
            map = it
        })

        imageButton_map_locate.setOnClickListener {
            startSearchBasedOnLocation()
        }
    }

    private fun observeError() {
        placesViewModel.getError().observe(activity, Observer {
            Snackbar.make(home_bottom_nav, it.toString(), Snackbar.LENGTH_LONG).show()
        })
    }

    private fun observeLoadingStatus() {
        placesViewModel.getLoadingStatus().observe(this, Observer<Int> {
            main_progressBar.visibility = it!!
        })
    }

    private fun observePlacesData() {
        placesViewModel.getPlacesLiveData().observe(this, Observer<PlacesResult> {
            if (it?.status == "OK" && it.results != null) {
                val points = mutableListOf<LatLng>()
                it.results
                        .map {
                            points.add(LatLng(it.geometry!!.location!!.lat, it.geometry.location!!.lng))
                            MarkerOptions()
                                    .position(LatLng(it.geometry.location.lat, it.geometry.location.lng))
                                    .title(it.name)
                        }
                        .forEach { marker -> map?.addMarker(marker) }

                val center = CameraUpdateFactory.newLatLng(centerOfPositions(points))
                val zoom = CameraUpdateFactory.zoomTo(12f)

                map?.moveCamera(center)
                map?.animateCamera(zoom)
            }
        })
    }

    private fun centerOfPositions(points : List<LatLng>) : LatLng {
        var latitude = 0.0
        var longitude = 0.0
        var amountOfPoints = points.size

        for (point in points) {
            latitude += point.latitude
            longitude += point.longitude
        }
        return LatLng(latitude/amountOfPoints, longitude/amountOfPoints);
    }




    private fun startSearchBasedOnLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_FINE_LOCATION)

        } else {
            placesViewModel.loadPlaces()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    placesViewModel.loadPlaces()
                } else {
                    Toast.makeText(activity, "Sorry no search for you", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

}