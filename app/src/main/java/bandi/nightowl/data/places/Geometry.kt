package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Geometry(val viewport: Viewport? = null,
                    val location: Location? = null) : Parcelable