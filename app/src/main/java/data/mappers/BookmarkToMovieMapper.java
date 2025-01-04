package data.mapper;

import android.util.Log;

import data.model.Bookmark;
import data.model.Favorite;
import data.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class BookmarkToMovieMapper {

    public static Movie map(Bookmark bookmark) {
        // Create a new Movie object from the Favorite object
        Log.d("Poster",  bookmark.getPosterPath());
        return new Movie(
                bookmark.getMovieId(),
                bookmark.getTitle(),
                bookmark.getOverview(),
                bookmark.getPosterPath()
        );
    }

    public static List<Movie> map(List<Bookmark> bookmarks) {
        List<Movie> movies = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            movies.add(map(bookmark));
        }
        return movies;
    }
}
