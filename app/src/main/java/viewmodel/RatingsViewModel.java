package viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import data.dao.RatingsDao;
import data.database.AppDatabase;
import data.model.Rating;

public class RatingsViewModel extends ViewModel {
    private RatingsDao ratingsDao;
    private LiveData<Rating> ratingLiveData;

    public RatingsViewModel(Application application) {
        super();
        AppDatabase db = AppDatabase.getDatabase(application);
        ratingsDao = db.ratingsDao();
    }

    public LiveData<Rating> getMovieRating(int movieId) {
        return ratingsDao.getRatingByMovieId(movieId);
    }

    public void addRatingOrUpdate(Rating rating) {
        new Thread(() -> {
            Rating existingRating = ratingsDao.getRatingByMovieIdSync(rating.getMovieId());
            if (existingRating != null) {
                rating.setId(existingRating.getId());
                ratingsDao.updateRating(rating);
            } else {
                ratingsDao.insertRating(rating);
            }
        }).start();
    }
}
