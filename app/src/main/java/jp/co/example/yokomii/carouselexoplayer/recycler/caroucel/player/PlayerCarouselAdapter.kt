package jp.co.example.yokomii.carouselexoplayer.recycler.caroucel.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import jp.co.example.yokomii.carouselexoplayer.R
import jp.co.example.yokomii.carouselexoplayer.media.MediaData
import jp.co.example.yokomii.carouselexoplayer.player.PlayerManager

class PlayerCarouselAdapter(
    private val playerManager: PlayerManager,
    private val medias: List<MediaData>,
) : RecyclerView.Adapter<PlayerCarouselAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_exo_player, parent, false)
        return ViewHolder(
            view,
            playerManager,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(medias[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun getItemCount() = medias.size

    class ViewHolder(
        private val view: View,
        private val playerManager: PlayerManager,
    ) : RecyclerView.ViewHolder(view) {

        private lateinit var mediaData: MediaData

        fun onBind(mediaData: MediaData) {
            this.mediaData = mediaData
            val playerView = view.findViewById<PlayerView>(R.id.playerView)
            playerView.player = playerManager.getPlayer(mediaData)
        }

        fun onRecycled() {
            playerManager.releasePlayer(mediaData)
            val playerView = view.findViewById<PlayerView>(R.id.playerView)
            playerView.player = null
        }
    }
}
