package com.example.cinequest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import data.model.Movie;

public class MovieDetails extends AppCompatActivity {
    private static final String ARG_MOVIE_TITLE = "movie_title";
    private static final String ARG_MOVIE_OVERVIEW = "movie_overview";
    private static final String ARG_MOVIE_POSTER = "movie_poster";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        TextView titleTextView = findViewById(R.id.movieTitle);
        TextView overViewTextView = findViewById(R.id.movieOverview);
        ImageView posterImageView = findViewById(R.id.moviePoster);
        Movie movie = null;
        if(getIntent().hasExtra("details_screen")){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                movie =(Movie) getIntent().getSerializableExtra("details_screen", Movie.class);
            }
        }
        if(movie != null){
            titleTextView.setText(movie.getTitle());
            overViewTextView.setText(movie.getOverview());
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(posterImageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
