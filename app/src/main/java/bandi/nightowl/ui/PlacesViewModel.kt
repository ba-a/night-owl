package bandi.nightowl.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import android.view.View
import bandi.nightowl.data.api.PlacesApi
import bandi.nightowl.data.places.PlacesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by abauer on 02.11.17.
 */
class PlacesViewModel @Inject constructor(private val prefs: SharedPreferences,
                                          private val retrofit: Retrofit) : ViewModel() {

    private var placesLiveData: MutableLiveData<PlacesResult>? = null

    private var disposables = CompositeDisposable()

    private val loadingStatus = MutableLiveData<Int>()

    private val error = MutableLiveData<String>()

    fun getPlacesLiveData(): LiveData<PlacesResult> {
        if (placesLiveData == null) {
            placesLiveData = MutableLiveData<PlacesResult>()
        }
        return placesLiveData as MutableLiveData<PlacesResult>
    }

    fun getLoadingStatus(): MutableLiveData<Int> {
        return loadingStatus
    }

    fun getError(): MutableLiveData<String> {
        return error
    }

    fun loadPlaces() {
        val placesApi = retrofit.create<PlacesApi>(PlacesApi::class.java)
        val placesCall = placesApi.fetchNearbyPlaces()

        disposables.add(placesCall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ loadingStatus.setValue(View.VISIBLE) })
                .doAfterTerminate ({ loadingStatus.setValue(View.GONE) })
                .subscribe(
                        { result -> placesLiveData?.postValue(result) },
                        { throwable -> error.postValue("An error happened")}
                ))
    }

    override fun onCleared() {
        disposables.clear()
    }

}