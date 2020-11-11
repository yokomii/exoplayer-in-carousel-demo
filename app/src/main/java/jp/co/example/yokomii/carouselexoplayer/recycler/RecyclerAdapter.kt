package jp.co.example.yokomii.carouselexoplayer.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import jp.co.example.yokomii.carouselexoplayer.R
import jp.co.example.yokomii.carouselexoplayer.media.MediaData
import jp.co.example.yokomii.carouselexoplayer.recycler.caroucel.dummy.DummyCarouselViewHolder
import jp.co.example.yokomii.carouselexoplayer.recycler.caroucel.player.PlayerCarouselViewHolder

class RecyclerAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val medias: List<MediaData>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class ListItems {
        DUMMY_A,
        PLAYER_CAROUSEL,
        DUMMY_B,
        DUMMY_C,
        DUMMY_D,
        DUMMY_E,
        DUMMY_F,
        DUMMY_G,
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_carousel, parent, false)

        return when (ListItems.values()[viewType]) {
            ListItems.PLAYER_CAROUSEL -> PlayerCarouselViewHolder(
                view,
                medias,
                lifecycleOwner,
            )
            else -> DummyCarouselViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is PlayerCarouselViewHolder -> holder.onBind()
            else -> Unit
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        return when (holder) {
            is PlayerCarouselViewHolder -> holder.onViewAttached()
            else -> Unit
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        return when (holder) {
            is PlayerCarouselViewHolder -> holder.onViewDetached()
            else -> Unit
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        return when (holder) {
            is PlayerCarouselViewHolder -> holder.onRecycled()
            else -> Unit
        }
    }

    override fun getItemCount() = ListItems.values().size

    override fun getItemViewType(position: Int) = position
}
