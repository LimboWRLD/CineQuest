package data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import data.model.Bookmark;

@Dao
public interface BookmarksDao {
    @Insert
    void addBookmark(Bookmark bookmark);

    @Query("SELECT * FROM bookmarks")
    LiveData<List<Bookmark>> getAllBookmarks();

    @Query("SELECT * FROM bookmarks WHERE movieId=:movieId")
    LiveData<Bookmark> getBookmarkById(int movieId);

    @Delete
    void deleteBookmark(Bookmark bookmark);
}
