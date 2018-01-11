package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Northeast(val lng: Double = 0.0,
                     val lat: Double = 0.0) : Parcelable