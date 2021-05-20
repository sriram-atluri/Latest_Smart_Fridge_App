package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryScreenFragment;
import com.example.myapplication.firstFragments.NotificationsFragment;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.nex3z.notificationbadge.NotificationBadge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.widget.ImageButton;

public class InventoryScreen extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton homeButton;
    private ImageButton notificationsButton;
    private ImageButton profilePageButton;
    NotificationBadge testBadge;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent();

        InventoryScreenFragment hm = new InventoryScreenFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.inventoryPlaceholder, hm)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            finish();
        });

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v -> {
            finish();
        });

        // UNCOMMENT LATER
        //testBadge = (NotificationBadge) findViewById(R.id.NotifBadge);

        //testBadge.setNumber(1);

        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.inventoryPlaceholder, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        profilePageButton = findViewById(R.id.ProfileButton);
        profilePageButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
        });
    }

}