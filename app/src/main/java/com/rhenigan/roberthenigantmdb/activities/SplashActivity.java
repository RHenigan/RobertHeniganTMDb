package com.rhenigan.roberthenigantmdb.activities;

import android.content.Intent;
import android.os.Bundle;
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

        Button nextPage = findViewById(R.id.nextPage);

        //CharSequence used to place Movie Title in Italics from the String Resources File
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        CharSequence[] mRandomQuote = new CharSequence[]{getText(R.string.quote0),
                getText(R.string.quote1),
                getText(R.string.quote2),
                getText(R.string.quote3),
                getText(R.string.quote4),
                getText(R.string.quote5),
                getText(R.string.quote6),
                getText(R.string.quote7),
                getText(R.string.quote8),
                getText(R.string.quote9)};

        CharSequence quote = mRandomQuote[new Random().nextInt(mRandomQuote.length)];
        welcomeMessage.setText(quote);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
