package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.google.api.services.youtube.model.SearchResult;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.api.services.youtube.model.Thumbnail;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.checkerframework.checker.units.qual.A;

public class youtubePlayer extends AppCompatActivity{
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private String videoId, Title, Artist;
    private PlayerConstants.PlayerState currentState;
    private YouTubePlayerTracker youTubePlayerTracker;
    private SearchResult searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "creating");
        setContentView(R.layout.youtubeplayer);
        super.onCreate(savedInstanceState);
        youTubePlayerTracker = new YouTubePlayerTracker();
        // Retrieve the videoId from the intent's extras
        videoId = getIntent().getStringExtra("videoId");
        Title = getIntent().getStringExtra("Title");
        Artist = getIntent().getStringExtra("Artist");
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.enableBackgroundPlayback(true);
        CreateNotification.createNotification(this, Title, Artist);
        playVideo(videoId);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
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
                youTubePlayer.addListener(youTubePlayerTracker);
            }
        });
    }
}