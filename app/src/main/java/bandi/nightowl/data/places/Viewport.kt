package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Viewport(val southwest: Southwest? = null,
                    val northeast: Northeast? = null): Parcelable