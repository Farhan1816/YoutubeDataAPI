package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HttpTransport HTTP_TRANSPORT = new com.google.api.client.http.javanet.NetHttpTransport();
    JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private EditText searchBar;
    private Button searchButton;
    private RecyclerView videoList;
    private YouTube youtube;
    private List<SearchResult>searchResults;
    private SearchListAdapter searchResultsAdapter;
    private final String API_KEY = "AIzaSyAw1JTHRHzvysTJaFuobEzOMckuIrm4QAc";
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        videoList = findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));


        searchResults = new ArrayList<>();
        searchResultsAdapter = new SearchListAdapter(searchResults);
        videoList = findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        videoList.setAdapter(searchResultsAdapter);
        createChannel();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchButton", "Search button clicked!!!!");
                searchResults.clear();
                String query = searchBar.getText().toString();
                if (!query.isEmpty()) {
                    searchYoutubeVideos(query);
                    Integer val = searchResults.size();
                    Log.d("prob", val.toString());
                }
            }
        });
    }

    private void createChannel() {
        Log.d("debug", "creatingChannel");
        NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNELID,
                "Farhan", NotificationManager.IMPORTANCE_DEFAULT);

        notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null){
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notificationManager.cancelAll();
    }
    private void searchYoutubeVideos(final String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
                            .setApplicationName("MusicMaster")
                            .setYouTubeRequestInitializer(new YouTubeRequestInitializer(API_KEY))
                            .build();

                    searchResults.clear();
                    YouTube.Search.List search = youtube.search().list("id,snippet");
                    search.setKey(API_KEY);
                    search.setQ(query);
                    search.setType("video");
                    search.setMaxResults(10L);
                    SearchListResponse searchResponse = search.execute();
                    searchResults.addAll(searchResponse.getItems());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchResultsAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the exception gracefully
                }
            }
        }).start();
    }
}