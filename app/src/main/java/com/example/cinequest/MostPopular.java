package com.example.cinequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import data.model.Movie;
import data.model.MoviesViewModel;
import ui.MovieAdapter;

public class MostPopular extends Fragment {

    private MoviesViewModel moviesViewModel;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);

        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);


        RecyclerView recyclerViewMostPopular = view.findViewById(R.id.mostPopular);
        recyclerViewMostPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Movie movie) {

                showMovieDetails(movie);
            }
        });
        recyclerViewMostPopular.setAdapter(movieAdapter);


        moviesViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.submitList(movies);
            }
        });

        moviesViewModel.fetchPopularMovies(requireContext());
        return view;
    }

    private void showMovieDetails(Movie movie) {

        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath()
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
