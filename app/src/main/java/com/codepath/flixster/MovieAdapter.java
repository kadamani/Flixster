package com.codepath.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.models.Config;
import com.codepath.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by samerk on 6/21/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    // list of movies
    ArrayList<Movie> movies;

    // config needed for image urls
    Config config;
    // context for rendering
    Context context;
    // initiailize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    // create and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the movie data at the specified position
        Movie movie = movies.get(position);
        // populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        // determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;

        //  build image url with config object
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        // if in portrait mode load poster image
        if (isPortrait) {
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        }
        else {
            // load the backdrop image
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        }

        // get the correct placeholder and imageview depending on orientation
        int placeHolderId = isPortrait ? R.drawable.flicks_movie_placeholder :
                R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.lvPosterImage : holder.ivBackdropImage;
        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(placeHolderId)
                .into(imageView);
    }

    // returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();

    }

    // create the viewholder as a static nested inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        @Override
        public void onClick(View v) {
            // get position of item
            int pos = getAdapterPosition();
            // make sure position is valid
            if (pos != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(pos);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }

        // track view objects
        @Nullable @BindView(R.id.lvPosterImage) ImageView lvPosterImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @Nullable @BindView(R.id.ivBackdropImage) ImageView ivBackdropImage;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder (View itemView)  {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // lookup view objects by id
//            lvPosterImage =
//            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
//            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
//            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            // add this to itemView onClickListener
            itemView.setOnClickListener(this);
        }
    }
}
