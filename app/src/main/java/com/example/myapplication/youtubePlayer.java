package com.example.myapplication;

import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class youtubePlayer extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private String videoId;
    private float currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeplayer);

        // Retrieve the videoId from the intent's extras
        videoId = getIntent().getStringExtra("videoId");
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.enableBackgroundPlayback(true);
        playVideo(videoId);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        youTubePlayerView.release();
    }
    private void playVideo(String videoId) {
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer Player) {
                youTubePlayer = Player;
                Player.loadVideo(videoId, 0);
            }
        });
    }
}