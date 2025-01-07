package ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinequest.R;

import java.util.ArrayList;
import java.util.List;

import data.factory.AppViewModelFactory;
import data.model.Bookmark;
import data.model.Movie;
import data.model.Rating;
import ui.adapter.MovieAdapter;
import ui.util.FragmentNavigationUtil;
import viewmodel.BookmarksViewModel;
import viewmodel.MoviesViewModel;
import viewmodel.RatingsViewModel;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_MOVIE_TITLE = "movie_title";
    private static final String ARG_MOVIE_OVERVIEW = "movie_overview";
    private static final String ARG_MOVIE_POSTER = "movie_poster";
    private static final String ARG_MOVIE_GENRES = "genres";

    public static MovieDetailsFragment newInstance(int movieId, String title, String overview, String posterPath, ArrayList<String> genresNew) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_TITLE, title);
        args.putString(ARG_MOVIE_OVERVIEW, overview);
        args.putString(ARG_MOVIE_POSTER, posterPath);
        args.putStringArrayList(ARG_MOVIE_GENRES, genresNew);
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
        TextView genresTextView = view.findViewById(R.id.genres);

        AppViewModelFactory factory = new AppViewModelFactory(requireActivity().getApplication());
        BookmarksViewModel bookmarksViewModel = new ViewModelProvider(this, factory).get(BookmarksViewModel.class);
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(2.5f);
        ratingBar.setStepSize(.5f);
        RatingsViewModel ratingsViewModel = new ViewModelProvider(this, factory).get(RatingsViewModel.class);

        if (getArguments() != null) {
            int movieId = getArguments().getInt(ARG_MOVIE_ID);
            String title = getArguments().getString(ARG_MOVIE_TITLE);
            String overview = getArguments().getString(ARG_MOVIE_OVERVIEW);
            String posterPath = getArguments().getString(ARG_MOVIE_POSTER);
            ArrayList<String> genres = getArguments().getStringArrayList(ARG_MOVIE_GENRES);
            genresTextView.setText(String.join(", ", genres));
            titleTextView.setText(title);
            overviewTextView.setText(overview);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + posterPath).into(posterImageView);
            ratingsViewModel.getMovieRating(movieId).observe(getViewLifecycleOwner(), rating -> {
                if (rating != null) {
                    ratingBar.setRating(rating.getRating());
                } else {
                    ratingBar.setRating(2.5f);
                }
            });
            ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
                if (fromUser) {
                    Rating newRating = new Rating(movieId, rating);
                    ratingsViewModel.addRatingOrUpdate(newRating);
                }
            });
            Button bookmarkBtn = view.findViewById(R.id.favoriteButton);
            bookmarksViewModel.getBookmarkById(movieId).observe(getViewLifecycleOwner(), bookmark -> {
                if (bookmark == null) {
                    bookmarkBtn.setText("Add Bookmark");
                    bookmarkBtn.setOnClickListener(v -> {
                        Bookmark newBookmark = new Bookmark(movieId, title, posterPath, overview, genres);
                        bookmarksViewModel.addBookmark(newBookmark);
                    });
                } else {
                    bookmarkBtn.setText("Remove Bookmark");
                    bookmarkBtn.setOnClickListener(v -> bookmarksViewModel.deleteBookmark(bookmark));
                }
            });
            RecyclerView recyclerView = view.findViewById(R.id.recommendedList);

            MovieAdapter movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
                @Override
                public void onClick(int position, Movie movie) {
                    FragmentNavigationUtil.showMovieDetails(requireActivity(), movie);
                }
            });
            MoviesViewModel moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);

            recyclerView.setAdapter(movieAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            moviesViewModel.getRecommendedMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    movieAdapter.submitList(movies);
                }
            });
            moviesViewModel.fetchRecommendedMovies(getContext(), movieId);

        }
    }
}
