package com.example.cinequest;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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


public class MovieSearchFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);

        // Initialize ViewModel
        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);

        // Set up RecyclerView
        RecyclerView recyclerViewMostPopular = view.findViewById(R.id.recyclerView);
        recyclerViewMostPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Movie movie) {
                // Handle the click on a movie
                showMovieDetails(movie);
            }
        });
        recyclerViewMostPopular.setAdapter(movieAdapter);

        // Observe LiveData for popular movies
        moviesViewModel.getSearchMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.submitList(movies);
            }
        });

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optionally handle search submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    movieAdapter.submitList(new ArrayList<>());
                } else {
                    moviesViewModel.searchMovies(requireContext(), newText);
                }
                return true;
            }
        });

        return view;
    }

    private void showMovieDetails(Movie movie) {
        // Replace the current fragment with MovieDetailsFragment and pass movie data
        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath()
        );

        // Use FragmentTransaction to replace the fragment
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, fragment)  // Assuming 'main' is the container in your layout
                .addToBackStack(null)  // Optional: To allow navigating back
                .commit();
    }
}