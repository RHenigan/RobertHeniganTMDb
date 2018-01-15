package com.rhenigan.roberthenigantmdb.clients;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rhenigan.roberthenigantmdb.VolleyRequestQueue;
import com.rhenigan.roberthenigantmdb.callbacks.VolleyCallback;

/**
 * Created by henig on 1/13/2018.
 * The VolleyClient receives the information from the TMDbClient and places the GET request to the
 * received URL, on a successful GET request the VolleyClient returns the resposne String to the
 * TMDbClient to be parsed
 */

public class VolleyClient {

    private static final String TAG = "VolleyClient";

    public static void getRequest(Context context, String URL, final VolleyCallback callback) {

        //Retrieve the Volley RequestQueue
        VolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();

        //If network is not available callback to TMDbClient
        if (!isNetworkAvailable(context)) {
            callback.onNetworkError();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                //Send response string back to TMDb on success
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "That didn't work!");
            }
        });

        //Places GET request into the Volley Request Queue
        VolleyRequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
