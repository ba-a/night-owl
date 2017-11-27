package bandi.nightowl.data.usecase

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject




class LocationUseCase(val context: Context) : LocationCallback() {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val subject = PublishSubject.create<Location>()

    fun listenToLocationRequests() : Observable<Location> {
        return subject
    }

    @SuppressLint("MissingPermission")
    fun getLocation() : Observable<Location> {
        val locationListener = this

        // Wait for the GoogleApiClient to be connected
        fusedLocationClient.lastLocation.addOnCompleteListener {
            if(it.result == null) {
                fusedLocationClient.requestLocationUpdates(LocationRequest.create(), locationListener, context.mainLooper)
            } else {
                subject.onNext(it.result)
            }
        }
        return subject

    }

    override fun onLocationResult(locationResult: LocationResult) {
        subject.onNext(locationResult.lastLocation)
        fusedLocationClient.removeLocationUpdates(this)
    }



}