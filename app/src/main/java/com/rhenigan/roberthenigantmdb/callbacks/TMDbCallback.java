package com.rhenigan.roberthenigantmdb.callbacks;

import com.rhenigan.roberthenigantmdb.dataClasses.Movie;

import java.util.List;

/**
 * Created by henig on 1/13/2018.
 * This Callback will provide Main Activity with the Movie List to update the RecyclerView
 */

public interface TMDbCallback {
    void onSuccess(List<Movie> movies);
    void onNetworkError();
}
