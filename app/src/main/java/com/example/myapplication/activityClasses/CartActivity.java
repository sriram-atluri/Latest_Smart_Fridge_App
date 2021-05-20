package com.example.myapplication.activityClasses;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.R;
import com.example.myapplication.firstFragments.NotificationsFragment;
import com.example.myapplication.cartFiles.CartAdapter;
import com.example.myapplication.cartFiles.ShoppingCartFragment;
import com.nex3z.notificationbadge.NotificationBadge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recycler_cart;
    private ImageButton backButton;
    private ImageButton notificationsButton;
    private ImageButton homeButton;
    private NotificationBadge cartPageNotifBadge;
    private ImageButton profilePageButton;
    CartAdapter cartList;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     // Derives the attributes from the saved instance state
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ShoppingCartFragment hm = new ShoppingCartFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cartContainer, hm)
                .commit();*./

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getIntent();

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.cartContainer, new ShoppingCartFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity2.class));
        });


        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft2.replace(R.id.cartContainer, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft2.commit();
        });

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v -> {
            finish();
        });

        // UNCOMMENT LATER
        //cartPageNotifBadge = findViewById(R.id.NotifBadge);
        //cartPageNotifBadge.setNumber(1);

        profilePageButton = findViewById(R.id.ProfileButton);
        profilePageButton.setOnClickListener(v->{
            i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });

        }


}
