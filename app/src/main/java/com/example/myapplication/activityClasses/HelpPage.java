package com.example.myapplication.activityClasses;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.R;
import com.example.myapplication.firstFragments.NotificationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.widget.ImageButton;

public class HelpPage extends AppCompatActivity {

    private ImageButton HomeButton;
    private ImageButton backButton;
    private ImageButton profileButton;
    private ImageButton notifButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent();

        HelpFragment aboutFragment = new HelpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.helpPageContainer, aboutFragment).commit();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        notifButton = findViewById(R.id.NotifButton);
        notifButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.helpPageContainer, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        HomeButton = findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(v->{
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });
    }
}