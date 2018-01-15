package com.rhenigan.roberthenigantmdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rhenigan.roberthenigantmdb.dataClasses.Movie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by henig on 1/13/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieDataSet;
    private Context mContext;
    private String TAG = "MovieAdapter";

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDate;
        TextView mOverview;
        ImageView mPoster;

        ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.releaseDate);
            mOverview = itemView.findViewById(R.id.overview);
            mPoster = itemView.findViewById(R.id.poster);
        }
    }

    public MovieAdapter(List<Movie> movieDataSet, Context context) {
        this.movieDataSet = movieDataSet;
        this.mContext = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, final int position) {
        final Movie movie = movieDataSet.get(position);

        holder.mTitle.setText(movie.getTitle());
        holder.mDate.setText(formatReleaseDate(movie.getReleaseDate()));

        //Display movie overview when user presses "Overview"
        holder.mOverview.setText("Overview");
        holder.mOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder overviewDialog = new AlertDialog.Builder(mContext);
                overviewDialog.setTitle("Overview");
                overviewDialog.setMessage(movie.getOverview());
                overviewDialog.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                overviewDialog.show();
            }
        });

        //Using Glide to show movie banner in movie_card, using error icon as a placeholder
        Glide.with(mContext)
                .load(mContext.getResources().getString(R.string.imageURL) + movieDataSet.get(position).getBackdropPath())
                .apply(placeholderOf(R.drawable.ic_error_black_24dp))
                .apply(fitCenterTransform())
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return movieDataSet.size();
    }

    //Convert date format from yyyy-MM-dd to Month dd, yyyy
    private String formatReleaseDate(String releaseDate) {
        String newReleaseDate;
        String expectedFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedFormat);
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        try {
            Date date = formatter.parse(releaseDate);
            newReleaseDate = df.format(date);
        } catch (ParseException e) {
            //If reformatting fails maintain the yyyy-MM-dd format
            Log.e(TAG, "error formatting date");
            newReleaseDate = releaseDate;
        }
        return newReleaseDate;
    }
}
