package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    int id;
    String BASE_IMAGE_PATH= "http://image.tmdb.org/t/p/w342//";
    String temporary_poster_path;
    String complete_poster_path;
    TextView movie_title;
    ImageView movie_poster;
    TextView movie_release_date;
    TextView movie_rating;
    TextView movie_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -88);
        Double vote_average = intent.getDoubleExtra("vote_average", -999.999);
        String original_title = intent.getStringExtra("original_title");
        String overview = intent.getStringExtra("overview");
        String poster_path = intent.getStringExtra("poster_path");
        String release_date = intent.getStringExtra("release_date");
        temporary_poster_path = poster_path;
        complete_poster_path = BASE_IMAGE_PATH + temporary_poster_path;

        movie_title = (TextView)findViewById(R.id.movie_title);
        movie_poster = (ImageView) findViewById(R.id.movie_poster);
        movie_release_date = (TextView)findViewById(R.id.movie_release_date);
        movie_rating = (TextView)findViewById(R.id.moving_rating);
        movie_description = (TextView)findViewById(R.id.movie_description);

        movie_title.setText(original_title);
        Picasso.with(this).load(complete_poster_path).into(movie_poster);
        movie_release_date.setText(release_date);
        movie_rating.setText(vote_average+"");
        movie_description.setText(overview);
    }
}
