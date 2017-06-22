package com.codepath.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samerk on 6/21/17.
 */

public class Config {
    // the base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images
    String posterSize;
    // backdrop size to use for fetching images
    String backdropSize;
    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // use option 3 in the array
        posterSize = posterSizeOptions.optString(3, "w342");
        // parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");

    }

    // helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size, path); // concatenate all 3 strings
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
