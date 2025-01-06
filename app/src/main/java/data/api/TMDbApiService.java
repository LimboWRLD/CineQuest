package data.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMDbApiService {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "272d2d2fbd538dc752999cb00976b65b";

    public void getPopularMovies(Context context, final MovieCallback callback) {
        String url = BASE_URL + "movie/popular?api_key=" + API_KEY + "&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            callback.onSuccess(results);
                        } catch (JSONException e) {
                            callback.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void searchMovies(Context context, String query, final MovieCallback callback) {
        String url = BASE_URL + "search/movie?api_key=" + API_KEY + "&query=" + query + "&include_adult=false&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            callback.onSuccess(results);
                        } catch (JSONException e) {
                            callback.onError("Error parsing data: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Error fetching data: " + error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



    public void getGenres(Context context, final MovieCallback callback) {
        String url = BASE_URL + "genre/movie/list?api_key=" + API_KEY + "&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("genres");
                            callback.onSuccess(results);
                        } catch (JSONException e) {
                            callback.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void getRecommendedMovies(Context context, int movieId, MovieCallback callback) {
        String url = BASE_URL + "movie/" + movieId + "/recommendations?api_key=" + API_KEY + "&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            callback.onSuccess(results);
                        } catch (JSONException e) {
                            callback.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface MovieCallback {
        void onSuccess(JSONArray movies);
        void onError(String errorMessage);
    }
}
