package viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import data.dao.BookmarksDao;
import data.database.AppDatabase;
import data.model.Bookmark;

public class BookmarksViewModel extends ViewModel {
    private BookmarksDao bookmarksDao;

    private LiveData<Bookmark> bookmarks;

    public BookmarksViewModel(Application application){
        super();
        AppDatabase db = AppDatabase.getDatabase(application);
        bookmarksDao = db.bookmarksDao();
    }

    public LiveData<List<Bookmark>> getBookmarks() {
        return bookmarksDao.getAllBookmarks();
    }

    public LiveData<Bookmark> getBookmarkById(int movieId){
        return bookmarksDao.getBookmarkById(movieId);
    }

    public void addBookmark(Bookmark bookmark){
        new Thread(()-> bookmarksDao.addBookmark(bookmark)).start();
    }

    public void deleteBookmark(Bookmark bookmark){
        new Thread(()-> bookmarksDao.deleteBookmark(bookmark)).start();
    }
}
