package jp.co.example.yokomii.carouselexoplayer.recycler.caroucel.player

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import jp.co.example.yokomii.carouselexoplayer.R
import jp.co.example.yokomii.carouselexoplayer.media.MediaData
import jp.co.example.yokomii.carouselexoplayer.player.PlayerManager

class PlayerCarouselViewHolder(
    view: View,
    private val mediaList: List<MediaData>,
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.ViewHolder(view),
    LifecycleObserver {

    private val playerManager: PlayerManager = PlayerManager(view.context)

    private var currentPosition = 0

    private var isViewAttached = false
        set(value) {
            field = value
            onVisibilityChanged()
        }
    private var isVisibleParent = false
        set(value) {
            field = value
            onVisibilityChanged()
        }

    private val adapter = PlayerCarouselAdapter(
        playerManager,
        mediaList,
    )

    private val onScrolledListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position =
                        layoutManager.findFirstVisibleItemPosition().takeIf { it >= 0 }
                            ?: return

                    if (position != currentPosition) {
                        currentPosition = position
                        playerManager.onCurrentMediaChanged(mediaList[position])
                    }
                }
            }
        }
    }

    init {
        val title = view.findViewById<TextView>(R.id.title)
        title.setText(R.string.carousel_title_player)

        val carousel = view.findViewById<RecyclerView>(R.id.carousel)
        PagerSnapHelper().attachToRecyclerView(carousel)
        carousel.adapter = adapter
        carousel.addOnScrollListener(onScrolledListener)
    }

    fun onBind() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun onViewAttached() {
        isViewAttached = true
    }

    fun onViewDetached() {
        isViewAttached = false
    }

    fun onRecycled() {
        // lifecycleOwner.lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        isVisibleParent = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        isVisibleParent = false
    }

    private fun onVisibilityChanged() {
        val isVisibleToUser = isViewAttached && isVisibleParent
        when {
            isVisibleToUser -> {
                playerManager.onCurrentMediaChanged(mediaList[currentPosition])
                adapter.notifyDataSetChanged()
            }
            else -> {
                playerManager.releaseAllPlayer()
            }
        }
    }
}
