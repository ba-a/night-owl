package bandi.nightowl.data.places

data class PhotosItem(val photoReference: String = "",
                      val width: Int = 0,
                      val htmlAttributions: List<String>? = null,
                      val height: Int = 0)