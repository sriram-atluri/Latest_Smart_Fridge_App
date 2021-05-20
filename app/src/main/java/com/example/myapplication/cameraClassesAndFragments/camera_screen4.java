package com.example.myapplication.cameraClassesAndFragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageButton;

public class camera_screen4 extends AppCompatActivity {

    private ImageButton mainBackButton;
    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageButton homeButton;
    private ImageButton profilePageButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mainBackButton = findViewById(R.id.backButton);
        backButton = findViewById(R.id.backButtonFromCamera4);
        forwardButton = findViewById(R.id.forwardButtonCamera4);
        homeButton = findViewById(R.id.HomeButton);
        profilePageButton = findViewById(R.id.ProfileButton);

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(this, camera_screen3.class);
            startActivity(i);
        });

        forwardButton.setOnClickListener(v-> {
            //Intent i = new Intent(this, CameraActivity.class);
            startActivity(i);
        });

        homeButton.setOnClickListener(v-> {
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        profilePageButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });
    }
}