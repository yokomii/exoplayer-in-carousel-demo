package jp.co.example.yokomii.carouselexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import jp.co.example.yokomii.carouselexoplayer.media.MediaData
import jp.co.example.yokomii.carouselexoplayer.recycler.RecyclerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = RecyclerAdapter(
            this,
            createMediaList(),
        )
    }

    private fun createMediaList() = (0L until 100).map {
        MediaData(
            id = it,
            mediaUrl = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8",
        )
    }
}
