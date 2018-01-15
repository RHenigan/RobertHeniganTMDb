package com.rhenigan.roberthenigantmdb.callbacks;

/**
 * Created by henig on 1/13/2018.
 * This Callback will send the Response from The Movie Database to the TMDbClient so it can parse
 * the response using GSON before giving the movie list to MainActivity
 */

public interface VolleyCallback {
    void onSuccess(String response);
    void onNetworkError();
}
