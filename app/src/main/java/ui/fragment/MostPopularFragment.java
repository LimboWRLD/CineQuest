package ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinequest.R;

import java.util.ArrayList;
import java.util.List;

import data.model.Movie;
import ui.util.FragmentNavigationUtil;
import viewmodel.MoviesViewModel;
import ui.adapter.MovieAdapter;

public class MostPopularFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);

        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);


        RecyclerView recyclerViewMostPopular = view.findViewById(R.id.recommendedList);
        recyclerViewMostPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Movie movie) {
                FragmentNavigationUtil.showMovieDetails(requireActivity(), movie);
            }
        });
        recyclerViewMostPopular.setAdapter(movieAdapter);

        moviesViewModel.fetchGenres(requireContext());

        moviesViewModel.getGenres().observe(getViewLifecycleOwner(), genres -> {
            if (genres != null && !genres.isEmpty()) {
                moviesViewModel.fetchPopularMovies(requireContext());
            }
        });

        moviesViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.submitList(movies);
            }
        });


        return view;
    }
}
