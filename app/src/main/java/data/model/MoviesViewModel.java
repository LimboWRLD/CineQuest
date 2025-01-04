package data.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import data.network.TMDbApiService;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> popularMovies;
    private MutableLiveData<List<Movie>> searchMovies;
    private MutableLiveData<String> errorMessage;
    private final TMDbApiService apiService;

    public MoviesViewModel() {
        apiService = new TMDbApiService();
        popularMovies = new MutableLiveData<>();
        searchMovies = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<Movie>> getSearchMovies() {
        return searchMovies;
    }

    public void fetchPopularMovies(Context context) {
        apiService.getPopularMovies(context, new TMDbApiService.MovieCallback() {
            @Override
            public void onSuccess(JSONArray movies) {
                List<Movie> movieList = new ArrayList<>();
                try {
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movieJson = movies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(movieJson.getInt("id"));
                        movie.setTitle(movieJson.getString("title"));
                        movie.setPosterPath(movieJson.getString("poster_path"));
                        movie.setOverview(movieJson.getString("overview"));
                        movie.setVoteAverage(movieJson.getDouble("vote_average"));
                        movieList.add(movie);
                    }
                    popularMovies.postValue(movieList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing movies. " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error (e.g., show a Toast)
            }
        });
    }

    public void searchMovies(Context context, String query) {
        apiService.searchMovies(context, query, new TMDbApiService.MovieCallback() {
            @Override
            public void onSuccess(JSONArray movies) {
                List<Movie> movieList = new ArrayList<>();
                try {
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movieJson = movies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(movieJson.getInt("id"));
                        movie.setTitle(movieJson.getString("title"));
                        movie.setPosterPath(movieJson.getString("poster_path"));
                        movie.setOverview(movieJson.getString("overview"));
                        movie.setVoteAverage(movieJson.getDouble("vote_average"));
                        movieList.add(movie);
                    }
                    searchMovies.postValue(movieList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing movies. " + e.getMessage());

                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error (e.g., show a Toast)
            }
        });
    }
}
