package bandi.nightowl.data.usecase

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class LocationUseCase(val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLocation() : Observable<Location> {
        val subject = PublishSubject.create<Location>()

        // Wait for the GoogleApiClient to be connected
        fusedLocationClient.lastLocation.addOnCompleteListener {
            subject.onNext(it.result)
            subject.onComplete()
        }
        return subject

    }


}