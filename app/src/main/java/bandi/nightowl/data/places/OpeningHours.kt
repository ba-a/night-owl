package bandi.nightowl.data.places

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class OpeningHours(@SerializedName("open_now") val openNow: Boolean = false) : Parcelable