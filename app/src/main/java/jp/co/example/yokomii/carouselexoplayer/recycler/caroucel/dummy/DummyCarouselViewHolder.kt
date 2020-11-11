package jp.co.example.yokomii.carouselexoplayer.recycler.caroucel.dummy

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.example.yokomii.carouselexoplayer.R

class DummyCarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    init {
        val title = view.findViewById<TextView>(R.id.title)
        title.setText(R.string.carousel_title_dummy)

        val carousel = view.findViewById<RecyclerView>(R.id.carousel)
        carousel.adapter = DummyCarouselAdapter()
    }
}
