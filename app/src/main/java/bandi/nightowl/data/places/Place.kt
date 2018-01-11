package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Place(val reference: String = "",
                 val types: List<String>? = null,
                 val scope: String = "",
                 val icon: String = "",
                 val name: String = "",
                 @SerializedName("opening_hours") val openingHours: OpeningHours? = null,
                 val rating: Double = 0.0,
                 val geometry: Geometry? = null,
                 val vicinity: String = "",
                 val id: String = "",
                 val photos: List<PhotosItem>? = null,
                 val placeId: String = "") : Parcelable