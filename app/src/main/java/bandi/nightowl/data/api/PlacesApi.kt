package bandi.nightowl.data.api

import bandi.nightowl.data.places.PlacesResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by abauer on 02.11.17.
 */
interface PlacesApi {

    @Headers("Accept: application/json")
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=48.137154,11.576124&radius=500&types=food&key=AIzaSyC0B2U9K9bPgw_PFnVygn7eU2ZjsN4kNts")
    fun fetchNearbyPlaces(): Single<PlacesResult>
}

