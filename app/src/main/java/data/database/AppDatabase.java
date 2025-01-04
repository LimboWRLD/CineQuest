package data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import data.dao.BookmarksDao;
import data.dao.FavoritesDao;
import data.dao.RatingsDao;
import data.model.Bookmark;
import data.model.Favorite;
import data.model.Rating;

@Database(entities = {Rating.class, Bookmark.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract BookmarksDao bookmarksDao();
    public abstract RatingsDao ratingsDao();


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "cinequest_database_1_1_1")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
