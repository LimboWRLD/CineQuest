package data.model;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import data.dao.RatingsDao;
import data.database.AppDatabase;

public class RatingsViewModel extends ViewModel {
    private RatingsDao ratingsDao;
    private LiveData<Rating> ratingLiveData;

    public RatingsViewModel(Application application){
        super();
        AppDatabase db = AppDatabase.getDatabase(application);
        ratingsDao = db.ratingsDao();
    }

    public LiveData<Rating> getMovieRating(int movieId){
        return ratingsDao.getRatingByMovieId(movieId);
    }

    public void addRating(Rating rating){
        new Thread(()-> ratingsDao.addRating(rating));
    }

    public void updateRating(Rating rating){
        new Thread(()-> ratingsDao.updateRating(rating));
    }
}
