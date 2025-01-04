package com.example.cinequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import data.factory.BookmarksViewModelFactory;
import data.model.Bookmark;
import data.model.BookmarksViewModel;
import data.model.Favorite;
import data.model.FavoritesViewModel;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_MOVIE_TITLE = "movie_title";
    private static final String ARG_MOVIE_OVERVIEW = "movie_overview";
    private static final String ARG_MOVIE_POSTER = "movie_poster";

    public static MovieDetailsFragment newInstance(int movieId,String title, String overview, String posterPath) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_TITLE, title);
        args.putString(ARG_MOVIE_OVERVIEW, overview);
        args.putString(ARG_MOVIE_POSTER, posterPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleTextView = view.findViewById(R.id.movieTitle);
        TextView overviewTextView = view.findViewById(R.id.movieOverview);
        ImageView posterImageView = view.findViewById(R.id.moviePoster);
        BookmarksViewModelFactory factory = new BookmarksViewModelFactory(requireActivity().getApplication());
        BookmarksViewModel bookmarksViewModel = new ViewModelProvider(this, factory).get(BookmarksViewModel.class);

        if (getArguments() != null) {
            int movieId = getArguments().getInt(ARG_MOVIE_ID);
            String title = getArguments().getString(ARG_MOVIE_TITLE);
            String overview = getArguments().getString(ARG_MOVIE_OVERVIEW);
            String posterPath = getArguments().getString(ARG_MOVIE_POSTER);

            titleTextView.setText(title);
            overviewTextView.setText(overview);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + posterPath).into(posterImageView);
            Button bookmarkBtn = view.findViewById(R.id.favoriteButton);
            bookmarksViewModel.getBookmarkById(movieId).observe(getViewLifecycleOwner(), bookmark -> {
                if (bookmark == null) {
                    // Movie is not bookmarked
                    bookmarkBtn.setText("Add Bookmark");
                    bookmarkBtn.setOnClickListener(v -> {
                        Bookmark newBookmark = new Bookmark(movieId, title, posterPath, overview);
                        bookmarksViewModel.addBookmark(newBookmark);
                    });
                } else {
                    // Movie is bookmarked
                    bookmarkBtn.setText("Remove Bookmark");
                    bookmarkBtn.setOnClickListener(v -> bookmarksViewModel.deleteBookmark(bookmark));
                }
            });
        }
    }
}
