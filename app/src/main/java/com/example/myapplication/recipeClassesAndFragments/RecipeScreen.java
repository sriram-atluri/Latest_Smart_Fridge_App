package com.example.myapplication.recipeClassesAndFragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.R;
import com.example.myapplication.firstFragments.NotificationsFragment;
import com.nex3z.notificationbadge.NotificationBadge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Button;
import android.widget.ImageButton;

public class RecipeScreen extends AppCompatActivity {

    private Button findButton;
    private ImageButton homeButton;
    private ImageButton notificationsButton;
    private NotificationBadge notifBadge;
    private ImageButton accountSettingsButton;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecipeFragment hm = new RecipeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_placeholder, hm)
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

        getIntent();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            finish();
        });

        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.recipe_placeholder, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v -> {

            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);

        });

        // UNCOMMENT LATER
        //notifBadge = (NotificationBadge) findViewById(R.id.NotifBadge);

        //notifBadge.setNumber(1);

        accountSettingsButton = findViewById(R.id.ProfileButton);
        accountSettingsButton.setOnClickListener(v->{
            startActivity(new Intent(this, ProfileSettingsActivity.class));
        });
    }
}