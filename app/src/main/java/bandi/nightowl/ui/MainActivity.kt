package bandi.nightowl.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import bandi.nightowl.R
import bandi.nightowl.data.places.PlacesResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private var map: GoogleMap? = null
    private lateinit var placesViewModel : PlacesViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home_bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_map -> {
                    navigator.showMap()
                    true
                }
                else -> {
                    // TODO not implemented yet
                    true
                }
            }
        }

        placesViewModel = ViewModelProviders.of(this, viewModelFactory).get(PlacesViewModel::class.java)
        observePlacesData()
        observeLoadingStatus()
        observeError()

    }

    private fun observeError() {
        placesViewModel.getError().observe(this, Observer {
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

            if(it?.status == "OK" && it.results != null) {
                it.results
                        .map {
                            MarkerOptions()
                                    .position(LatLng(it.geometry!!.location!!.lat, it.geometry.location!!.lng))
                                    .title(it.name)
                        }
                        .forEach { marker -> map?.addMarker(marker) }

                val center = CameraUpdateFactory.newLatLng(LatLng(48.137154,
                        11.576124))
                val zoom = CameraUpdateFactory.zoomTo(12f)

                map?.moveCamera(center)
                map?.animateCamera(zoom)
            }
        })
    }


    val navigator = object : HomeNavigator {
        override fun showSearch() {
            // TODO
        }

        override fun showMap() {
            var mapFragment = supportFragmentManager.findFragmentByTag("MAP")

            if(mapFragment == null)
                mapFragment = SupportMapFragment.newInstance()

            mapFragment as SupportMapFragment
            mapFragment.getMapAsync(OnMapReadyCallback {
                map = it
                placesViewModel.loadPlaces()
            })

            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.home_content, mapFragment, "MAP")
                    .addToBackStack(null)
                    .commit()
        }

        override fun showList() {
            // Todo implement
        }

    }

}
