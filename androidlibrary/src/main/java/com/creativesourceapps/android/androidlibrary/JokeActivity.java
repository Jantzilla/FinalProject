package com.creativesourceapps.android.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        textView = (TextView) findViewById(R.id.text_view_three);

        String joke = getIntent().getStringExtra("joke");

        textView.setText(joke);
    }
}
