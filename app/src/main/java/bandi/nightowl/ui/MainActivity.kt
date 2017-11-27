package bandi.nightowl.ui

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import bandi.nightowl.R
import bandi.nightowl.ui.maps.PlacesMapFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

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
    }

    val navigator = object : HomeNavigator {
        override fun showSearch() {
            // TODO
        }

        override fun showMap() {
            var mapFragment = supportFragmentManager.findFragmentByTag("MAP")

            if(mapFragment == null)
                mapFragment = PlacesMapFragment.newInstance()

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
