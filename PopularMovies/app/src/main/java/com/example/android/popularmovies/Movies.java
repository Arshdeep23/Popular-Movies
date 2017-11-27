package com.example.android.popularmovies;

/**
 * Created by Arshdeep on 11/22/2017.
 */

public class Movies {
    String original_title = "";
    String overview = "";
    String release_date = "";
    String poster_path = "";
    Double vote_average = null;
    int id;
    public Movies(String title, String overview, String date, String poster_path, Double vote_average, int id){
        this.original_title = title;
        this.overview = overview;
        this.release_date = date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public int getId() {
        return id;
    }
}
