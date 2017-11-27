package com.example.android.popularmovies.Utils;

/**
 * Created by Arshdeep on 11/23/2017.
 */

import com.example.android.popularmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Arshdeep on 11/20/2017.
 */

public class NetworkUtils {

    static ArrayList<Movies> movies;
    public static final String BASE_URL_STRING = "https://api.themoviedb.org/3/movie";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static String SORT_ORDER = TOP_RATED;
    public static final String API_KEY_TEXT = "api_key";
    //TODO : Add your own API_KEY to the API_KEY string below.
    public static final String API_KEY = "";
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String buildQuery() {
        String query = BASE_URL_STRING+"/"+ SORT_ORDER +"?"+ API_KEY_TEXT +"="+ API_KEY;
        return query;
    }

    public static ArrayList<Movies> displayParsedData(String response) {
        String original_title = "";
        String overview = "";
        String release_date = "";
        String poster_path = "";
        Double vote_average = null;
        int id;
        movies = new ArrayList<Movies>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject jsonObject1;
            int size = results.length();
            for(int i = 0;i<size;i++){
                jsonObject1 = results.getJSONObject(i);
                overview = jsonObject1.getString("overview");
                original_title = jsonObject1.getString("original_title");
                release_date = jsonObject1.getString("release_date");
                poster_path = jsonObject1.getString("poster_path");
                vote_average = jsonObject1.getDouble("vote_average");
                id = jsonObject1.getInt("id");
                movies.add(new Movies(original_title,overview, release_date, poster_path, vote_average, id));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}