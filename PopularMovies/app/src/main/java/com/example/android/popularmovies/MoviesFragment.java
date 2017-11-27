package com.example.android.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    Context context;
    TextView error_textview;
    private ArrayList<Movies> movieList;
    private RecyclerView recyclerView;
    MovieAdapter moviesAdapter;
    String query;
    FetchData fetchData;
    ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        context = getActivity();
        error_textview = (TextView)rootView.findViewById(R.id.error_textview);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_movies);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        query = NetworkUtils.buildQuery();

        FetchData fetchData = new FetchData();
        fetchData.execute(query);
        setHasOptionsMenu(true);
        return rootView;

    }
    public void showErrorMessage(){
        error_textview.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }
    public void showRecyclerView(){
        error_textview.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public class FetchData extends AsyncTask<String, Void, String> {
        URL url =null;
        HttpsURLConnection urlConnection;
        String response = "";

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String address = params[0];
            try {
                url = new URL(address);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                response = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(response.equals("")){
                showErrorMessage();
                progressBar.setVisibility(View.INVISIBLE);
            }
            else{
                showRecyclerView();
                progressBar.setVisibility(View.INVISIBLE);
                movieList = new ArrayList<Movies>();
                movieList = NetworkUtils.displayParsedData(response);

                moviesAdapter = new MovieAdapter(new MovieAdapter.MoviesAdapterOnClickHandler() {
                    @Override
                    public void onClick(int id, Double vote_average, String original_title, String overview, String poster_path, String release_date) {
                        Intent intent = new Intent(getActivity(), MovieDetail.class);
                        intent.putExtra("id",id);
                        intent.putExtra("vote_average", vote_average);
                        intent.putExtra("original_title", original_title);
                        intent.putExtra("overview", overview);
                        intent.putExtra("poster_path", poster_path);
                        intent.putExtra("release_date", release_date);
                        startActivity(intent);
                    }
                }, context, movieList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(moviesAdapter);
                moviesAdapter.notifyDataSetChanged();
            }

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.sorting,menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                NetworkUtils.SORT_ORDER = NetworkUtils.POPULAR;
                query = NetworkUtils.buildQuery();
                fetchData = new FetchData();
                fetchData.execute(query);
                break;
            case R.id.action_top_rated:
                NetworkUtils.SORT_ORDER = NetworkUtils.TOP_RATED;
                query = NetworkUtils.buildQuery();
                fetchData = new FetchData();
                fetchData.execute(query);
                break;
            default:
                break;
        }
        return true;
    }


}
