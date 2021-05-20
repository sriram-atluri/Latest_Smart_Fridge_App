package com.example.myapplication.activityClasses;

import android.content.Intent;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.cameraClassesAndFragments.Camera1Fragment;
import com.example.myapplication.firstFragments.NotificationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.widget.ImageButton;

import com.example.myapplication.R;
import com.nex3z.notificationbadge.NotificationBadge;

public class CameraActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton backwardButton;
    private ImageButton forwardButton;
    private ImageButton homeButton;
    private NotificationBadge cameraScreenNotifBadge;
    private ImageButton accountSettingsButton;
    private ImageButton notificationsButton;
    private ImageButton profilePageButton;
    NotificationBadge testBadge;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent();
        Camera1Fragment hm = new Camera1Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cameraPlaceholder, hm)
                .commit();
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity2.class));
        });

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v -> {
            finish();
        });

        //testBadge = (NotificationBadge) findViewById(R.id.NotifBadge);

        //testBadge.setNumber(1);

        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        profilePageButton = findViewById(R.id.ProfileButton);
        profilePageButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });
    }
}