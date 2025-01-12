package ui.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cinequest.R;

import ui.fragment.MovieDetailsFragment;

import data.model.Movie;

public class FragmentNavigationUtil {

    public static void showMovieDetails(FragmentActivity activity, Movie movie) {
        if (activity == null || movie == null) return;

        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getGenres()
        );

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }
}

