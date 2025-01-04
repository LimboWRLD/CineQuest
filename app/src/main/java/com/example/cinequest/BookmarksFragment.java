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

import data.factory.BookmarksViewModelFactory;
import data.model.Bookmark;
import data.model.BookmarksViewModel;
import data.model.Favorite;
import data.model.Movie;
import ui.MovieAdapter;


public class BookmarksFragment extends Fragment {

    private BookmarksViewModelFactory factory;
    private BookmarksViewModel bookmarksViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_bookmarks, container, false);
        factory = new BookmarksViewModelFactory(requireActivity().getApplication());
        bookmarksViewModel = new ViewModelProvider(this,factory).get(BookmarksViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MovieAdapter movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Movie movie) {

                showMovieDetails(movie);
            }
        });
        recyclerView.setAdapter(movieAdapter);

        bookmarksViewModel.getBookmarks().observe(getViewLifecycleOwner(), (Observer<? super List<Bookmark>>) new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                movieAdapter.submitList(data.mapper.BookmarkToMovieMapper.map(bookmarks));
            }
        });
        bookmarksViewModel.getBookmarks();
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