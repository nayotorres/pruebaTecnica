package com.torres.pruebatecnica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.torres.pruebatecnica.databinding.ActivityVideoBinding
import com.torres.pruebatecnica.util.Constants

class VideoActivity : YouTubeBaseActivity() {

    private lateinit var binding:ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoKey =  intent?.extras?.getString("videoId")

        binding.ytPlayer.initialize(Constants.api,object:YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1!!.loadVideo(videoKey)
                p1.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                if(p1?.isUserRecoverableError==true){
                    p1.getErrorDialog(this@VideoActivity,0).show()
                }else{

                }
            }
        })
    }
}