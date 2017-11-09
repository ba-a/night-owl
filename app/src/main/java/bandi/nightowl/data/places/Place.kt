package bandi.nightowl.data.places

data class Place(val reference: String = "",
                 val types: List<String>? = null,
                 val scope: String = "",
                 val icon: String = "",
                 val name: String = "",
                 val openingHours: OpeningHours? = null,
                 val rating: Double = 0.0,
                 val geometry: Geometry? = null,
                 val vicinity: String = "",
                 val id: String = "",
                 val photos: List<PhotosItem>? = null,
                 val placeId: String = "")