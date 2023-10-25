package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.services.youtube.model.SearchResult;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<SearchResult> searchResults;

    public SearchListAdapter(List<SearchResult> searchResults) {

        this.searchResults = searchResults;
        Integer val = searchResults.size();
        //Log.d("arrayval", val.toString());
        if(val!=0) {
            String need = searchResults.get(0).getSnippet().getTitle();
            Log.d("arrayval", need);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("OnSuccess", "Done1");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("OnSuccess", "Done2");
        SearchResult searchResult = searchResults.get(position);
        // Bind the search result data to the views in the layout.
        Picasso.get().load(searchResult.getSnippet().getThumbnails().getMedium().getUrl()).into(holder.thumbnail);
        holder.title.setText(searchResult.getSnippet().getTitle());
        holder.description.setText(searchResult.getSnippet().getDescription());
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView thumbnail;
        private TextView title;
        private TextView description;

        public ViewHolder(View view) {

            super(view);
            Log.d("OnSuccess", "Done3");
            thumbnail = view.findViewById(R.id.thumbnail);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
        }
    }
}
