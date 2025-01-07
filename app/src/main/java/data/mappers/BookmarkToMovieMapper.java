package data.mappers;

import android.util.Log;

import data.model.Bookmark;
import data.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class BookmarkToMovieMapper {

    public static Movie map(Bookmark bookmark) {
        Log.d("Poster", bookmark.getPosterPath());
        return new Movie(
                bookmark.getMovieId(),
                bookmark.getTitle(),
                bookmark.getOverview(),
                bookmark.getPosterPath(),
                bookmark.getGenres()
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
