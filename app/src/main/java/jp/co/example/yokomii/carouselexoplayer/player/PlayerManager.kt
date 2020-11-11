package jp.co.example.yokomii.carouselexoplayer.player

import android.content.Context
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import jp.co.example.yokomii.carouselexoplayer.media.MediaData

class PlayerManager(private val context: Context) {

    private val cache = mutableMapOf<MediaData, SimpleExoPlayer>()
    private var currentMedia: MediaData? = null

    fun onCurrentMediaChanged(mediaData: MediaData) {
        this.currentMedia = mediaData

        cache.filter { it.key != mediaData }
            .forEach {
                val player = it.value
                player.seekTo(0, 0)
                player.pause()
            }

        val player = getPlayer(mediaData)
        player.play()
    }

    fun getPlayer(mediaData: MediaData): SimpleExoPlayer {
        return cache[mediaData]
            ?: createPlayer()
                .also {
                    it.prepareMedia(mediaData)
                    cache[mediaData] = it
                }
                .also {
                    if (currentMedia == mediaData && !it.isPlaying) {
                        it.play()
                    }
                }
    }

    private fun createPlayer(): SimpleExoPlayer {
        return SimpleExoPlayer.Builder(context)
            .setTrackSelector(
                DefaultTrackSelector(context).apply {
                    setParameters(
                        buildUponParameters().setMaxVideoSize(640, 360)
                    )
                }
            )
            .build()
    }

    fun releasePlayer(mediaData: MediaData) {
        val player = cache.remove(mediaData)
        player?.release()
    }

    fun releaseAllPlayer() {
        cache.values.forEach {
            it.release()
        }
        cache.clear()
    }

    private fun SimpleExoPlayer.prepareMedia(mediaData: MediaData) {
        val mediaSource = mediaData.mapToMediaSource()
        setMediaSource(mediaSource)
        repeatMode = Player.REPEAT_MODE_ALL
        // volume = 0F
        prepare()
    }
}
