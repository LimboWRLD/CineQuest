package data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import data.model.Favorite;
import data.model.Movie;

@Dao
public interface FavoritesDao {
    @Insert
    void addFavorite(Favorite favorite);

    @Query("SELECT * FROM favorites")
    LiveData<List<Favorite>> getAllFavorites();

    @Delete
    void deleteFavorite(Favorite favorite);
}
