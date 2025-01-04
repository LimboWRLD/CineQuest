package data.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import data.dao.BookmarksDao;
import data.dao.FavoritesDao;
import data.database.AppDatabase;

public class FavoritesViewModel extends ViewModel {
    private BookmarksDao bookmarksDao;

    private LiveData<Bookmark> bookmarks;

    public FavoritesViewModel(Application application){
        super();
        AppDatabase db = AppDatabase.getDatabase(application);
        bookmarksDao = db.bookmarksDao();
    }

    public LiveData<List<Bookmark>> getFavorites() {
        Log.d("Baza", bookmarksDao.getAllBookmarks().toString());
        return bookmarksDao.getAllBookmarks();
    }

    public void addFavorite(Bookmark bookmark){
        new Thread(()-> bookmarksDao.addBookmark(bookmark));
    }

    public void deleteFavorite(Bookmark bookmark){
        new Thread(()-> bookmarksDao.deleteBookmark(bookmark));
    }
}
