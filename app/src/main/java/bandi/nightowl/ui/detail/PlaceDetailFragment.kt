package bandi.nightowl.ui.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import bandi.nightowl.BuildConfig

import bandi.nightowl.R
import bandi.nightowl.data.places.Place
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_place_detail.*


class PlaceDetailFragment : Fragment() {

    private var place: Place? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            place = arguments!!.getParcelable(placeParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView_detail_place_details.text = place?.name + "\n" +
                "isOpenNow " + place?.openingHours?.openNow + "\n" +
                "location " + place?.vicinity

        ratingBar_detail_place.setIsIndicator(true)
        place?.rating?.let {
            ratingBar_detail_place.rating = place!!.rating.toFloat()
        }

        val imageRef = place?.photos?.get(0)

        val imageRequest = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                imageRef?.photoReference + "&key=" + BuildConfig.API_KEY

        Glide.with(this).load(imageRequest).into(imageView_detail_header)
        imageView_detail_header.visibility = VISIBLE

    }

    companion object {
        private val placeParam = "placeParam"

        fun newInstance(place: Place): PlaceDetailFragment {
            val fragment = PlaceDetailFragment()
            val args = Bundle()
            args.putParcelable(placeParam, place)
            fragment.arguments = args
            return fragment
        }
    }

}
