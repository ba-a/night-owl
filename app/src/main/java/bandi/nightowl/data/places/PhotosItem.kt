package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class PhotosItem(@SerializedName("photo_reference") val photoReference: String = "",
                      val width: Int = 0,
                      val htmlAttributions: List<String>? = null,
                      val height: Int = 0) : Parcelable