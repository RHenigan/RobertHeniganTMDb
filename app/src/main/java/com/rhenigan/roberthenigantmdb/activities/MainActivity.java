package com.rhenigan.roberthenigantmdb.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.rhenigan.roberthenigantmdb.MovieAdapter;
import com.rhenigan.roberthenigantmdb.R;
import com.rhenigan.roberthenigantmdb.clients.TMDbClient;
import com.rhenigan.roberthenigantmdb.callbacks.TMDbCallback;
import com.rhenigan.roberthenigantmdb.dataClasses.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henig on 1/12/2018.
 * The Main Activity will be where the movie information will be displayed within the recycler view
 */

public class MainActivity extends AppCompatActivity implements TMDbCallback {

    private RecyclerView.Adapter mAdapter;
    public ProgressBar mProgressBar;
    private List<Movie> mMovieDataSet = new ArrayList<>();
    private Toolbar mToolbar;

    private int mFilter = 0;
    // To be used to select which URL is used against The Movie Database
    // mFilter = 0 --> Popular Movies
    // mFilter = 1 --> Top Rated Movies
    // mFilter = 2 --> Now Playing Movies
    // mFilter = 3 --> Upcoming Movies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.mainToolBar);
        mToolbar.setTitleTextColor(0xFF000000);
        setSupportActionBar(mToolbar);

        mProgressBar = findViewById(R.id.loadingBar);
        mProgressBar.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.movieRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(mMovieDataSet, this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //if recycler view reaches the end
                if (!recyclerView.canScrollVertically(1)) {
                    //increment the page counter for the current page (popular or top rated)
                    TMDbClient.getInstance(getApplicationContext(), MainActivity.this).incPage(mFilter);
                    //get the new movie list
                    TMDbClient.getInstance(getApplicationContext(), MainActivity.this).getMovies(mFilter);
                }
            }
        });

        //Request popular movies(mFilter = 0) from TMDb Client
        TMDbClient.getInstance(this.getApplicationContext(), this).getMovies(0);
        mToolbar.setTitle("Popular Movies");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /* When adding new search options to the menu be sure to use a new mFilter value
            * for each new search option*/
            case R.id.info:
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.refresh:
                //reset the page counter for the current movie page
                TMDbClient.getInstance(this.getApplicationContext(), this).resetPage(mFilter);
                updateMovieList(mFilter);
                return true;
            case R.id.popular:
                //Set mFilter for Popular Movie Search
                mFilter = 0;
                updateMovieList(mFilter);
                mToolbar.setTitle("Popular Movies");
                return true;
            case R.id.topRated:
                //Set mFilter for Top Rated Movie Search
                mFilter = 1;
                updateMovieList(mFilter);
                mToolbar.setTitle("Top Rated Movies");
                return true;
            case R.id.nowPlaying:
                //Set mFilter for the Now Playing Movie Search
                mFilter = 2;
                updateMovieList(mFilter);
                mToolbar.setTitle("Now Playing Movies");
                return true;
            case R.id.upcoming:
                //Set mFilter for the Upcoming Movie Search
                mFilter = 3;
                updateMovieList(mFilter);
                mToolbar.setTitle("Upcoming Movies");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Prevent user from returning to Splash Activity
    @Override
    public void onBackPressed() {}

    //Callback method for when information is successfully parsed by TMDbClient
    @Override
    public void onSuccess(List<Movie> movies) {
        mMovieDataSet.addAll(movies);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    //Network error has occurred and a dialog will be displayed
    @Override
    public void onNetworkError() {
        final AlertDialog.Builder overviewDialog = new AlertDialog.Builder(this);
        overviewDialog.setTitle("Network Error!");
        overviewDialog.setMessage(getString(R.string.networkErrorDialog));
        overviewDialog.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        overviewDialog.show();
    }

    //Used when user refreshes or performs a new search
    private void updateMovieList(int filter) {
        mMovieDataSet.clear();
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.VISIBLE);

        //Request movie list from The Movie Database with the mFilter that was passed
        TMDbClient.getInstance(this.getApplicationContext(), this).getMovies(filter);
    }
}
