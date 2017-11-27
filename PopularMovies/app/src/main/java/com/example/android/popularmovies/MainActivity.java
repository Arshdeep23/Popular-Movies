package com.example.android.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;
    TextView display;
    private ArrayList<Movies> movieList = new ArrayList<Movies>();
    private RecyclerView recyclerView;
    MovieAdapter moviesAdapter;
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}