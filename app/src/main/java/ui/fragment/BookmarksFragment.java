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

import data.factory.AppViewModelFactory;
import data.model.Bookmark;
import ui.util.FragmentNavigationUtil;
import viewmodel.BookmarksViewModel;
import data.model.Movie;
import ui.adapter.MovieAdapter;


public class BookmarksFragment extends Fragment {

    private AppViewModelFactory factory;
    private BookmarksViewModel bookmarksViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        factory = new AppViewModelFactory(requireActivity().getApplication());
        bookmarksViewModel = new ViewModelProvider(this, factory).get(BookmarksViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MovieAdapter movieAdapter = new MovieAdapter(new ArrayList<>(), new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Movie movie) {

                FragmentNavigationUtil.showMovieDetails(requireActivity(), movie);
            }
        });
        recyclerView.setAdapter(movieAdapter);

        bookmarksViewModel.getBookmarks().observe(getViewLifecycleOwner(), (Observer<? super List<Bookmark>>) new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                movieAdapter.submitList(data.mappers.BookmarkToMovieMapper.map(bookmarks));
            }
        });
        bookmarksViewModel.getBookmarks();

        return view;
    }
}