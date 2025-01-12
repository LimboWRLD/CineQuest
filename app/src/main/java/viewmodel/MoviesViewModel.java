package viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.api.TMDbApiService;
import data.model.Genre;
import data.model.Movie;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> popularMovies;
    private MutableLiveData<List<Movie>> searchMovies;
    private MutableLiveData<List<Genre>> genres;
    private MutableLiveData<List<Movie>> recommendedMovies;
    private Map<Integer, String> genreMap;

    public MutableLiveData<List<Genre>> getGenres() {
        return genres;
    }

    private MutableLiveData<String> errorMessage;
    private final TMDbApiService apiService;

    public MoviesViewModel() {
        apiService = new TMDbApiService();
        popularMovies = new MutableLiveData<>();
        searchMovies = new MutableLiveData<>();
        recommendedMovies = new MutableLiveData<>();
        genres = new MutableLiveData<>();
        genreMap = new HashMap<>();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }


    public LiveData<List<Movie>> getSearchMovies() {
        return searchMovies;
    }

    public LiveData<List<Movie>> getRecommendedMovies() {
        return recommendedMovies;
    }

    public void fetchPopularMovies(Context context) {
        apiService.getPopularMovies(context, new TMDbApiService.MovieCallback() {
            @Override
            public void onSuccess(JSONArray movies) {
                List<Movie> movieList = new ArrayList<>();
                try {
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movieJson = movies.getJSONObject(i);
                        Movie movie = parseMovie(movieJson);
                        movieList.add(movie);
                    }
                    popularMovies.postValue(movieList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing movies. " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error while fetching recomended movies:" + errorMessage);
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
                        Movie movie = parseMovie(movieJson);
                        movieList.add(movie);
                    }
                    searchMovies.postValue(movieList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing movies. " + e.getMessage());

                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error while fetching searched movies:" + errorMessage);

            }
        });
    }

    public void fetchGenres(Context context) {
        apiService.getGenres(context, new TMDbApiService.MovieCallback() {

            @Override
            public void onSuccess(JSONArray genresJSON) {
                List<Genre> genreList = new ArrayList<>();

                try {
                    for (int i = 0; i < genresJSON.length(); i++) {
                        JSONObject genreJson = genresJSON.getJSONObject(i);
                        Genre genre = new Genre(genreJson.getString("name"), genreJson.getInt("id"));
                        genreList.add(genre);
                        genreMap.put(genre.getId(), genre.getName());
                    }
                    genres.postValue(genreList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing genres. " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error while fetching genres:" + errorMessage);

            }
        });
    }

    public void fetchRecommendedMovies(Context context, int movieId) {
        apiService.getRecommendedMovies(context, movieId, new TMDbApiService.MovieCallback() {
            @Override
            public void onSuccess(JSONArray movies) {
                List<Movie> movieList = new ArrayList<>();
                try {
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movieJson = movies.getJSONObject(i);
                        Movie movie = parseMovie(movieJson);
                        movieList.add(movie);
                    }
                    recommendedMovies.postValue(movieList);
                } catch (JSONException e) {
                    errorMessage.postValue("Error parsing recommended movies. " + e.getMessage());
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error while fetching movies:" + errorMessage);
            }
        });
    }


    private Movie parseMovie(JSONObject movieJson) throws JSONException {
        Movie movie = new Movie();
        movie.setId(movieJson.getInt("id"));
        movie.setTitle(movieJson.getString("title"));
        movie.setPosterPath(movieJson.getString("poster_path"));
        movie.setOverview(movieJson.getString("overview"));
        movie.setVoteAverage(movieJson.getDouble("vote_average"));

        List<Integer> genreIds = new ArrayList<>();
        JSONArray genreIdsArray = movieJson.getJSONArray("genre_ids");
        for (int j = 0; j < genreIdsArray.length(); j++) {
            genreIds.add(genreIdsArray.getInt(j));
        }
        ArrayList<String> genreNames = new ArrayList<>();
        for (Integer genreId : genreIds) {
            String genreName = genreMap.get(genreId);
            if (genreName != null) {
                genreNames.add(genreName);
            }
        }
        movie.setGenres(genreNames);

        return movie;
    }
}
