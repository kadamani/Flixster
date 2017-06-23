package com.codepath.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by samerk on 6/21/17.
 */
@Parcel

public class Movie {
    // values from API
    Double voteAverage;
    public String title;
    public String overview;
    public String posterPath; // only the path
    public String backdropPath;
    public String id;

    // initialize from JSON data
    public Movie(JSONObject object) throws JSONException {
        voteAverage = object.getDouble("vote_average");
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        id = object.getString("id");
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

}
