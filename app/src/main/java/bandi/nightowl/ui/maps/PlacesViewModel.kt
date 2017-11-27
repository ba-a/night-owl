package bandi.nightowl.ui.maps

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import android.util.Log
import android.view.View
import bandi.nightowl.BuildConfig
import bandi.nightowl.data.api.PlacesApi
import bandi.nightowl.data.places.PlacesResult
import bandi.nightowl.data.usecase.LocationUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by abauer on 02.11.17.
 */
class PlacesViewModel @Inject constructor(private val retrofit: Retrofit,
                                          private val locationUseCase: LocationUseCase) : ViewModel() {

    private var placesLiveData: MutableLiveData<PlacesResult> = MutableLiveData<PlacesResult>()
    private var disposables = CompositeDisposable()
    private val loadingStatus = MutableLiveData<Int>()
    private val currentLocationLiveData = MutableLiveData<Location>()
    private val errorLiveData = MutableLiveData<String>()

    fun getPlacesLiveData(): LiveData<PlacesResult> {
        return placesLiveData
    }

    fun getCurrentLocationLiveData(): LiveData<Location> {
        disposables.add(locationUseCase.listenToLocationRequests().subscribe({
            location -> currentLocationLiveData.postValue(location)
        }))
        return currentLocationLiveData
    }

    fun getLoadingStatusLiveData(): MutableLiveData<Int> {
        return loadingStatus
    }

    fun getErrorLiveData(): MutableLiveData<String> {
        return errorLiveData
    }

    fun loadPlaces() {
        val placesApi = retrofit.create<PlacesApi>(PlacesApi::class.java)
        val locationCall = locationUseCase.getLocation()

        disposables.add(
                locationCall.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap {
                            val location = it.latitude.toString() + "," + it.longitude.toString()
                            return@flatMap placesApi.fetchNearbyPlaces(location, BuildConfig.API_KEY).toObservable()
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe({ loadingStatus.setValue(View.VISIBLE) })
                        .subscribe({
                            Log.i("Success", "places")
                            placesLiveData.postValue(it)
                            loadingStatus.setValue(View.GONE)
                        }, {
                            Log.e("Error", "places", it)
                            loadingStatus.setValue(View.GONE)
                        }))

    }

    override fun onCleared() {
        disposables.clear()
    }

}