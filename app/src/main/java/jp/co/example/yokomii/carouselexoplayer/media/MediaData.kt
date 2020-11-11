package jp.co.example.yokomii.carouselexoplayer.media

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

data class MediaData(
    val id: Long,
    val mediaUrl: String,
) {

    fun mapToMediaSource(): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory()
        return HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(mediaUrl))
    }
}
