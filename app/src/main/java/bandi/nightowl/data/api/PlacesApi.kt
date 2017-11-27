package bandi.nightowl.data.api

import bandi.nightowl.data.places.PlacesResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by abauer on 02.11.17.
 */
interface PlacesApi {

    @Headers("Accept: application/json")
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&types=bar&opennow")
    fun fetchNearbyPlaces(@Query("location") location :String, @Query("key") key :String): Single<PlacesResult>


}

