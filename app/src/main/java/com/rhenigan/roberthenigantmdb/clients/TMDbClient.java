package com.rhenigan.roberthenigantmdb.clients;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rhenigan.roberthenigantmdb.R;
import com.rhenigan.roberthenigantmdb.callbacks.TMDbCallback;
import com.rhenigan.roberthenigantmdb.callbacks.VolleyCallback;
import com.rhenigan.roberthenigantmdb.dataClasses.Movie;
import com.rhenigan.roberthenigantmdb.dataClasses.Result;

import java.util.List;

/**
 * Created by henig on 1/13/2018.
 * The TMDbClient will receive a request from the main activity with a filter flag
 * based on the filter the the TMDBClient will request the VolleyClient to place a GET Request
 * on a successful GET request the TMDbClient will be receive the json data (response) as a string
 * Once received the TMDbClient will parse the String into the Result and Movie Data Classes
 * After parsing the TMDbClient will pass the list of Movies to the MainActivity
 */

public class TMDbClient implements VolleyCallback {

    private static TMDbClient mInstance;
    private static Context mContext;
    private TMDbCallback mCallBack;
    private static final String TAG = "TMDbClient";
    private int mPopularPage = 1;
    private int mTopRatedPage = 1;
    private int mNowPlayingPage = 1;
    private int mUpcomingPage = 1;

    private TMDbClient(Context context, TMDbCallback callback) {
        mContext = context;
        mCallBack = callback;
    }

    public static synchronized TMDbClient getInstance(Context context, TMDbCallback callback) {
        if (mInstance == null) {
            mInstance = new TMDbClient(context, callback);
        }
        return mInstance;
    }

    public void getMovies(int filter) {
        String URL = mContext.getResources().getString(R.string.moviesURL)
                + mContext.getResources().getString(R.string.apiKey);

        switch (filter) {
            // filter is from MainActivity
            // mFilter = 0 --> Popular Movies
            // mFilter = 1 --> Top Rated Movies
            // mFilter = 2 --> Now Playing Movies
            // mFilter = 3 --> Upcoming Movies
            case 0:
                //inject the current page count into the URL
                String popularURL = String.format(URL, "popular", mPopularPage);
                VolleyClient.getRequest(mContext, popularURL , this);
                break;
            case 1:
                //inject the current page count into the URL
                String topRatedURL = String.format(URL, "top_rated", mTopRatedPage);
                VolleyClient.getRequest(mContext, topRatedURL, this);
                break;
            case 2:
                //inject the current page count into the URL
                String nowPlayingURL = String.format(URL, "now_playing", mNowPlayingPage);
                VolleyClient.getRequest(mContext, nowPlayingURL, this);
                break;
            case 3:
                //inject the current page count into the URL
                String upcomingURL = String.format(URL, "upcoming", mUpcomingPage);
                VolleyClient.getRequest(mContext, upcomingURL, this);
                break;
            default:
                //Popular Movies will be the default case
                //inject the current page count into the URL
                popularURL = String.format(URL, "popular", mPopularPage);
                VolleyClient.getRequest(mContext, popularURL, this);
                break;
        }
    }

    public void incPage(int filter) {
        switch (filter) {
            case 0:
                mPopularPage++;
                break;
            case 1:
                mTopRatedPage++;
                break;
            case 2:
                mNowPlayingPage++;
                break;
            case 3:
                mUpcomingPage++;
                break;
            default:
                break;
        }
    }

    public void resetPage(int filter) {
        switch (filter) {
            case 0:
                mPopularPage = 1;
                break;
            case 1:
                mTopRatedPage = 1;
                break;
            case 2:
                mNowPlayingPage = 1;
                break;
            case 3:
                mUpcomingPage = 1;
                break;
            default:
                break;
        }
    }

    //Callback method for when information is successfully received from the Database
    @Override
    public void onSuccess(String response) {
        List<Movie> movies = null;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //The response consists of the results which tells how many results and how many pages and
        //current page, here the response is parsed by GSON into the Result and Movie classes
        Result result = gson.fromJson(response, Result.class);
        try {
            //Nested inside the results is the movie data
            movies = result.getResults();
        } catch (Exception e) {
            Log.e(TAG, "No movie results retrieved");
        }

        //passing list of movies back to the MainActivity
        mCallBack.onSuccess(movies);
    }

    //Network not available notify MainActivity
    @Override
    public void onNetworkError() {
        mCallBack.onNetworkError();
    }
}
