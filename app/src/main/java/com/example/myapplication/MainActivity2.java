package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.services.iot.client.AWSIotDevice;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.firstFragments.HomeFragment;
import com.example.myapplication.firstFragments.NotificationDBHelper;
import com.example.myapplication.firstFragments.NotificationsFragment;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private final String TAG = AuthenticationActivity.class.getSimpleName();
    private static final AWSIotQos QOS = AWSIotQos.QOS1; // doesn't matter
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    private ImageButton recipeButton;
    private ImageButton inventoryScreenButton;
    private ImageButton cameraScreenButton;
    private ImageButton shoppingCartScreenButton;
    private Button QRScan;


    private ImageButton notificationsButton;
    private ImageButton homeButton;
    private ImageButton profileButton;

    private Button notificationView;


    MQTTHandler mqttHandler;
    boolean subscribed = false;

    String fridgeTemperatureValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //System.out.println("In main activity");            // Debug statement 1
        setContentView(R.layout.activity_main2);

        getIntent();

        /*
        try {
            Amplify.addPlugin( new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }*/

        try {
            mqttHandler = MQTTHandler.getInstance(this);    // Creates an instance of the MQTTHandler to handle the messaging subscriptions
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mqttHandler.enable_temp();
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fridgeTemperatureValue = mqttHandler.get_fridge_temp();

        //String fridgeTemperatureValue = "0";
        mqttHandler.disable_temp();

        /*try {
            mqttHandler = MQTTHandler.getInstance(getApplicationContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Amplify.addPlugin( new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }*/

        NotificationDBHelper notificationDBHelper = new NotificationDBHelper(this);
//        AWSMobileClient mobileClient = (AWSMobileClient) Amplify.Auth.getPlugin("awsCognitoAuthPlugin").getEscapeHatch();
//        mobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {            // The constructor that initalizes the application context for token creation
//            @Override
//            public void onResult(UserStateDetails result) {
//                Log.i(TAG, "Trialboy " + result.getUserState().toString());
//
//                switch (result.getUserState()) {
//                    case SIGNED_OUT:
//                        Log.e("INIT", "SIGNED_OUT");
//                        break;
//                    case SIGNED_IN:
//                        System.out.println("before sign in");
////
////                        // Creates an AWS Internet of Things Message Queueing Telemetry Transport client to iniate connection with Amazon's IOT service
////                        AWSIotMqttClient client = new AWSIotMqttClient("a3fqhlf39hbti2-ats.iot.us-west-2.amazonaws.com",
////                                "arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp/client/3405dqrmjes3j39lo0pgopnacc",
////                                "AKIAZR45JFDBI6WHY6AC",
////                                "59beme0r6dqNm36UEtR6rsTm4HvAVWFVRX/+J0XV");
////
////                        String smartFridgeARN = "arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp";
////                        AWSIotDevice smartFridge = new AWSIotDevice(smartFridgeARN);    // SomeDevice extends AWSIotDevice
////                        client.setNumOfClientThreads(2);
////
////                        try {
////                            //    client.attach(smartFridge);
////                            client.connect();                               // Connects to the client
////                            System.out.println("connection executed");      // If connection succeeds
////                        } catch (AWSIotException e) {
////                            e.printStackTrace();
////                        }
////
////                        AWSIotTopic topic =
////                                //                                   new AWSIotTopic("arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp/greeting", AWSIotQos.QOS0)
////                                new AWSIotTopic("rpi/test/tx", AWSIotQos.QOS1) {
////                                    @Override
////                                    public void onMessage(AWSIotMessage message) {
////                                        System.out.println("Entering on message");
////                                        String test  = message.getStringPayload();
////                                        System.out.println(test);
////                                        //System.out.println("Msg arrived = " + message.getStringPayload() + " from topic = " + message.getTopic());
////                                    }
////                                };
////
////
////                        try {
////                            client.subscribe(topic, true);                             // Subscribes the client to the topic
////                            Thread.sleep(60000);                                        // Establishes the sleep time for the thread
////                            client.disconnect();                                           // Disconnects the client
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
//                        //AWSMobileClient.getInstance().signOut();
//                        break;
//                    default:            // The default case
//                        AWSMobileClient.getInstance().signOut();
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//                System.out.println("Error!");
//            }
//        });

    }


    @Override
    protected void onStart() {
        super.onStart();


        /*try {
            MQTTHandler.getInstance(getApplicationContext());    // Creates an instance of the MQTTHandler to handle the messaging subscriptions
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        //if (!subscribed) {
        //mqttHandler.aws_subscribe();                                // Calls the aws_subscribe function
        //  subscribed = true;
        //}


        //System.out.println("Fridge Temperature Result is: " + fridgeTemperatureValue);       // Sets the fridgeTemperature TextView to display the value obtained from the get_fridge_temp function


        /*Toolbar toolbar = findViewById(R.id.HamburgerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/


        /*Button login = findViewById(R.id.button5);                                                      // Creates a login "Button" object

        login.setOnClickListener(v -> {
            Intent i = new Intent(this, AuthenticationActivity.class);                    // Declares and initializes the intent by specifying the origin class and the target class
            startActivity(i);                                                                 // Starts the activity of the target class
        });*/

        /*
        notificationView = findViewById(R.id.notificationview);
        notificationView.setOnClickListener(v->{
            Toast.makeText(this, notification, Toast.LENGTH_LONG).show();
        });*/

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();     // The policy to disable strict mode
        StrictMode.setThreadPolicy(policy);                                                             // Sets the policy



        Bundle bundle = new Bundle();                                                           // Creates a bundle object o pass data values to a fragment or activity
        bundle.putString("fridgeTemp", fridgeTemperatureValue);                                 // The fridge temperature value is mapped to the fridgeTemp key */
        HomeFragment hm = new HomeFragment();                                                   // Creates a HomeFragment object
        hm.setArguments(bundle);                                                                // Sends the arguments from the bundle
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.your_placeholder, hm)
                .commit();


        /*Button dbConnect = findViewById(R.id.button14);                                  // Database connect button

         Calls the ClickListener so it can listen to what the user wants
        dbConnect.setOnClickListener(v -> {
            System.out.println("Test");                                                                 // Debug statement 2
            //CreateItemAsyncTask createItem = new CreateItemAsyncTask();
            // Intent i = new Intent(this, targetClass.class);
            // startActivity(i);
            // new CreateItemAsyncTask().doInBackground();                                                 // Calls the doInBackgroundMethod from the Asynchronous Task
            Intent i = new Intent(this, RecipeTimeOfTheDay.class);
            startActivity(i);
        });*/
        /*
        recipeButton = findViewById(R.id.recipeButton);
        inventoryScreenButton = findViewById(R.id.inventoryScreen);
        cameraScreenButton = findViewById(R.id.cameraScreenDefault);
        shoppingCartScreenButton = findViewById(R.id.shoppingCart);

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
        });


        QRScan = findViewById(R.id.QR_SCAN);
        QRScan.setOnClickListener(v->{
            startActivity(new Intent(this,  QRAndBarcodeScanActivity.class));
        });*/

        //setContentView(R.id.nav_view);
        /*navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);*/
        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;

                if (item.getItemId() == R.id.nav_home){
                    Toast.makeText(getApplicationContext(), "Home is working", Toast.LENGTH_LONG).show();
                    //i = new Intent(MainActivity2.this, QRAndBarcodeScanActivity.class);
                    i = new Intent(MainActivity2.this, AboutPage.class);
                    startActivity(i);
                }
                else if (item.getItemId() == R.id.nav_gallery){
                    Toast.makeText(getApplicationContext(), "Going to contacts", Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity2.this, ContactsPage.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Going to help page", Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity2.this, HelpPage.class);
                    startActivity(i);
                }
                return true;
            }
        });*/

        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.your_placeholder, new NotificationsFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v->{
            /*Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);*/

            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.your_placeholder, new HomeFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(v->{
            Intent i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/



    private class CreateItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            System.out.println("Before getting instance");                                              // Debug statement 3
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity2.this);
            databaseAccess.create();
            System.out.println("After getting instance");                                               // Debug statement 4
            return null;
        }
    }

    private class DeleteItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            System.out.println("Before getting instance");                                              // Debug statement 3
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity2.this);
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
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MainActivity2.this);              // The program is failing at this area
            System.out.println("After getting instance");                                               // Debug statement 6
            return databaseAccess.getAll();
        }

    }
}