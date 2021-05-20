package com.example.myapplication.cameraClassesAndFragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageButton;

public class CameraScreen2 extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageButton accountSettingsButton;
    private ImageButton homeButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        backButton = findViewById(R.id.backButtonFromCamera2);
        forwardButton = findViewById(R.id.forwardButtonCamera2);
        homeButton = findViewById(R.id.HomeButton);
        accountSettingsButton = findViewById(R.id.ProfileButton);

        backButton.setOnClickListener(v -> {
            //i = new Intent(this, CameraActivity.class);
            startActivity(i);
        });

        forwardButton.setOnClickListener(v -> {
            i = new Intent(this, camera_screen3.class);
            startActivity(i);
        });

        homeButton.setOnClickListener(v ->{
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        accountSettingsButton.setOnClickListener(v ->{
            Intent i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });
    }
}