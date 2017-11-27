package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    public ArrayList<Movies> moviesList = new ArrayList<Movies>();
    String BASE_IMAGE_PATH= "http://image.tmdb.org/t/p/w342//";
    String temporary_poster_path;
    String complete_poster_path;
    Context context;
    final private MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler{
        public void onClick(int id, Double vote_average, String original_title, String overview, String poster_path, String release_date);
    }

    public MovieAdapter( MoviesAdapterOnClickHandler clickHandler, Context context, ArrayList<Movies> moviesList){
        this.moviesList = moviesList;
        this.mClickHandler = clickHandler;
        this.context = context;
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView movie_details ;
        ImageView movie_image;
        public MovieHolder(View itemView) {
            super(itemView);
            movie_details = (TextView)itemView.findViewById(R.id.movie_details);
            movie_image = (ImageView)itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int id = moviesList.get(adapterPosition).getId();
            Double vote_average = moviesList.get(adapterPosition).getVote_average();
            String original_title= moviesList.get(adapterPosition).getOriginal_title();
            String overview= moviesList.get(adapterPosition).getOverview();
            String poster_path = moviesList.get(adapterPosition).getPoster_path();
            String release_date = moviesList.get(adapterPosition).getRelease_date();
            mClickHandler.onClick(id, vote_average, original_title, overview, poster_path, release_date );
        }
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);


        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movies movies = moviesList.get(position);
        temporary_poster_path = movies.getPoster_path();
        complete_poster_path = BASE_IMAGE_PATH + temporary_poster_path;
        Picasso.with(context).load(complete_poster_path).into(holder.movie_image);
    }

    @Override
    public int getItemCount() {
        if(moviesList==null){
            return 0;
        }
        return moviesList.size();
    }
}
