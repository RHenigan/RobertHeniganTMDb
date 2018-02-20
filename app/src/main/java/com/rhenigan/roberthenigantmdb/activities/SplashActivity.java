package com.rhenigan.roberthenigantmdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rhenigan.roberthenigantmdb.R;
import com.rhenigan.roberthenigantmdb.activities.MainActivity;

import java.util.Random;

/**
 * Created by henig on 1/12/2018.
 * Splash activity will display a random quote from one of my favorite movies, when the user presses
 * the "Popular Movie" button Main Activity will be launched and search The Movie Database for
 * Popular Movies
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //CharSequence used to place Movie Title in Italics from the String Resources File
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        String[] mRandomQuote = getResources().getStringArray(R.array.quotes);

        CharSequence quote = mRandomQuote[new Random().nextInt(mRandomQuote.length)];
        welcomeMessage.setText(quote);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
