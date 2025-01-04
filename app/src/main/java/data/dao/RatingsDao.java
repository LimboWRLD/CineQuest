package data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import data.model.Rating;

@Dao
public interface RatingsDao {
    @Insert
    void addRating(Rating rating);

    @Update
    void updateRating(Rating rating);

    @Query("SELECT * FROM ratings")
    LiveData<List<Rating>> getAllRatings();

    @Query("SELECT * FROM ratings WHERE movieId = :movieId LIMIT 1")
    LiveData<Rating>  getRatingByMovieId(int movieId);
}
