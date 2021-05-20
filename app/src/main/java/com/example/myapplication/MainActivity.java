package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    // The method that is initiated when the screen is appearing for the first time
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("In main activity");                                                         // Debug statement 1
        setContentView(R.layout.activity_main);


// perform setOnClickListener event on Second Button

        //navigationView = (NavigationView) findViewById(R.id.;

        /*
        Button login = findViewById(R.id.button5);                                                      // Creates a login "Button" object

        login.setOnClickListener(v-> {
                    Intent i = new Intent(this, AuthenticationActivity.class);                    // Declares and initializes the intent by specifying the origin class and the target class
                    startActivity(i);                                                                 // Starts the activity of the target class
                });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();     // The policy to disable strict mode
        StrictMode.setThreadPolicy(policy);                                                             // Sets the policy


        Button dbConnect = findViewById(R.id.button14);                                  // Database connect button

        // Calls the ClickListener so it can listen to what the user wants
        dbConnect.setOnClickListener(v -> {
            System.out.println("Test");                                                                 // Debug statement 2
            CreateItemAsyncTask createItem = new CreateItemAsyncTask();
            // Intent i = new Intent(this, targetClass.class);
            // startActivity(i);
            new CreateItemAsyncTask().doInBackground();                                                 // Calls the doInBackgroundMethod from the Asynchronous Task
        });

        ImageButton recipeButton = findViewById(R.id.recipeButton);
        ImageButton inventoryScreenButton = findViewById(R.id.inventoryScreen);
        ImageButton cameraScreenButton = findViewById(R.id.cameraScreenDefault);
        ImageButton shoppingCartScreenButton = findViewById(R.id.shoppingCart);

        recipeButton.setOnClickListener(v -> {
            Intent i = new Intent(this, RecipeScreen1.class);                           // Creates an intent to indicate that the user would like to go from "this" menu to the "RecipeScreen1" menu
            startActivity(i);                                                                         // Enables the migration from the origin menu to the destination menu
        });

        inventoryScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(this, InventoryScreen.class);
            startActivity(i);
        });

        cameraScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(this, CameraScreen1.class);
            startActivity(i);
        });

        shoppingCartScreenButton.setOnClickListener(v -> {
            Intent i = new Intent (this, CartActivity.class);
            startActivity(i);
        });
        */
        // drawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button login = findViewById(R.id.button5);                                                      // Creates a login "Button" object

        login.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);                    // Declares and initializes the intent by specifying the origin class and the target class
            startActivity(i);                                                                 // Starts the activity of the target class
        });

        /*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();     // The policy to disable strict mode
        StrictMode.setThreadPolicy(policy);                                                             // Sets the policy


        Button dbConnect = findViewById(R.id.button14);                                  // Database connect button

        // Calls the ClickListener so it can listen to what the user wants
        dbConnect.setOnClickListener(v -> {
            System.out.println("Test");                                                                 // Debug statement 2
            //CreateItemAsyncTask createItem = new CreateItemAsyncTask();
            // Intent i = new Intent(this, targetClass.class);
            // startActivity(i);
           // new CreateItemAsyncTask().doInBackground();                                                 // Calls the doInBackgroundMethod from the Asynchronous Task
            Intent i = new Intent(this, RecipeTimeOfTheDay.class);
            startActivity(i);
        });

        ImageButton recipeButton = findViewById(R.id.recipeButton);
        ImageButton inventoryScreenButton = findViewById(R.id.inventoryScreen);
        ImageButton cameraScreenButton = findViewById(R.id.cameraScreenDefault);
        ImageButton shoppingCartScreenButton = findViewById(R.id.shoppingCart);

        recipeButton.setOnClickListener(v -> {
            Intent i = new Intent(this, RecipeScreen1.class);                           // Creates an intent to indicate that the user would like to go from "this" menu to the "RecipeScreen1" menu
            startActivity(i);                                                                         // Enables the migration from the origin menu to the destination menu
        });

        inventoryScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(this, InventoryScreen.class);
            startActivity(i);
        });

        cameraScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(this, CameraScreen1.class);
            startActivity(i);
        });

        shoppingCartScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
        });*/
        //setContentView(R.id.nav_view);
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //drawerLayout = findViewById(R.id.drawer_layout);
        //toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(true);
        //toggle.syncState();

        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "Home is working", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity.this, AboutPage.class);
                        startActivity(i);
                        //drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_gallery:
                        Toast.makeText(getApplicationContext(), "Going to contacts", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity.this, ContactsPage.class);
                        startActivity(i);
                    case R.id.nav_slideshow:
                        Toast.makeText(getApplicationContext(), "Going to help page", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity.this, HelpPage.class);
                        startActivity(i);
                }
               if (item.getItemId() == R.id.nav_home){
                    Toast.makeText(getApplicationContext(), "Home is working", Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity.this, AboutPage.class);
                    startActivity(i);
                }
                else if (item.getItemId() == R.id.nav_gallery){
                    Toast.makeText(getApplicationContext(), "Going to contacts", Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity.this, ContactsPage.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Going to help page", Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity.this, HelpPage.class);
                    startActivity(i);
                }
                return true;
            }
        });

    } */


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }*/

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

        //public boolean

    /*
    private class CreateItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            System.out.println("Before getting instance");                                              // Debug statement 3
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
            databaseAccess.create();
            System.out.println("After getting instance");                                               // Debug statement 4
            return null;
        }
    }

    private class DeleteItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            System.out.println("Before getting instance");                                              // Debug statement 3
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);
            databaseAccess.delete();
            System.out.println("After getting instance");                                               // Debug statement 4
            return null;
        }
    }


    // This class derives the functionality of AsyncTask to perform the task asynchronously
    private class GetAllItemsAsyncTask extends AsyncTask<Void, Void, List<Document>> {
        @Override
        protected List<Document> doInBackground(Void... params) {
            System.out.println("Before getting instance");                                              // Debug statement 5
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity.this);              // The program is failing at this area
            System.out.println("After getting instance");                                               // Debug statement 6
            return databaseAccess.getAll();
        }

    }

*/
    }
}
