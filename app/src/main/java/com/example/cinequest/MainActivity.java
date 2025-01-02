package com.example.cinequest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import data.model.Movie;
import data.network.TMDbApiService;
import ui.MovieAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mostPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter
        movieAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);

        // Fetch popular movies
        TMDbApiService apiService = new TMDbApiService();
        apiService.getPopularMovies(this, new TMDbApiService.MovieCallback() {
            @Override
            public void onSuccess(JSONArray movies) {
                try {
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movieJson = movies.getJSONObject(i);
                        String title = movieJson.getString("title");
                        String posterPath = movieJson.getString("poster_path");
                        String overview = movieJson.getString("overview");
                        double voteAverage = movieJson.getDouble("vote_average");
                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setPosterPath(posterPath);
                        movie.setOverview(overview);
                        movie.setVoteAverage(voteAverage);
                        movieList.add(movie);
                    }
                    movieAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error parsing movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, "Error fetching movies: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}