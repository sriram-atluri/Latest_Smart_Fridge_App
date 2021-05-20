package com.example.myapplication.cameraClassesAndFragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.*;
import com.example.myapplication.R;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageButton;

public class camera_screen3 extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageButton homeButton;
    private ImageButton profilePageButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    @Override
    protected void onStart() {
        super.onStart();
        backButton = findViewById(R.id.backButtonFromCamera3);
        forwardButton = findViewById(R.id.forwardButtonCamera3);
        homeButton = findViewById(R.id.HomeButton);
        profilePageButton = findViewById(R.id.ProfileButton);

        backButton.setOnClickListener(v -> {
            i = new Intent(this, CameraScreen2.class);
            startActivity(i);
        });

        forwardButton.setOnClickListener(v -> {
            i = new Intent(this, camera_screen4.class);
            startActivity(i);
        });

        homeButton.setOnClickListener(v-> {
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        profilePageButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });

    }

}