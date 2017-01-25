package com.surya.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra("Joke");
        String[] message = joke.split("-");
        TextView farmerText = (TextView)findViewById(R.id.farmer_message);
        TextView strangerText = (TextView)findViewById(R.id.stranger_message);
        farmerText.setText(message[1]);
        strangerText.setText(message[0]);

    }
}
