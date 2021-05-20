package com.example.myapplication.profileSettingsClassesAndFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.amazonaws.services.iot.client.AWSIotQos;
import com.example.myapplication.AuthenticationActivity;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.firstFragments.NotificationsFragment;
import com.nex3z.notificationbadge.NotificationBadge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileSettingsActivity extends AppCompatActivity {

    private final String TAG = AuthenticationActivity.class.getSimpleName();
    private static final AWSIotQos QOS = AWSIotQos.QOS1; // doesn't matter
    private Button settings;
    private ImageButton notificationsButton, homeButton, backButton;
    private NotificationBadge profScreenNotifBadge;
    private SharedPreferences prefs;
    public static final String myPreferences = "MyPrefs";

    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView phoneNo;
    private TextView address;

    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*
        AWSMobileClient mobileClient = (AWSMobileClient) Amplify.Auth.getPlugin("awsCognitoAuthPlugin").getEscapeHatch();

        mobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {            // The constructor that initalizes the application context for token creation
            @Override
            public void onResult(UserStateDetails result) {
                Log.i(TAG, "Trialboy " + result.getUserState().toString());

                switch (result.getUserState()) {
                    case SIGNED_OUT:
                        Log.e("INIT", "SIGNED_OUT");
                        break;
                    case SIGNED_IN:
                        System.out.println("before sign in");

                        // Creates an AWS Internet of Things Message Queueing Telemetry Transport client to iniate connection with Amazon's IOT service
                        AWSIotMqttClient client = new AWSIotMqttClient("a3fqhlf39hbti2-ats.iot.us-west-2.amazonaws.com",
                                "arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp/client/3405dqrmjes3j39lo0pgopnacc",
                                "AKIAZR45JFDBI6WHY6AC",
                                "59beme0r6dqNm36UEtR6rsTm4HvAVWFVRX/+J0XV");

                        String smartFridgeARN = "arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp";
                        AWSIotDevice smartFridge = new AWSIotDevice(smartFridgeARN);    // SomeDevice extends AWSIotDevice
                        client.setNumOfClientThreads(2);

                        try {
                            //    client.attach(smartFridge);
                            client.connect();                               // Connects to the client
                            System.out.println("connection executed");      // If connection succeeds
                        } catch (AWSIotException e) {
                            e.printStackTrace();
                        }

                        AWSIotTopic topic =
                                //                                   new AWSIotTopic("arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp/greeting", AWSIotQos.QOS0)
                                new AWSIotTopic("greeting", AWSIotQos.QOS1) {
                                    @Override
                                    public void onMessage(AWSIotMessage message) {
                                        System.out.println("Msg arrived = " + message.getStringPayload() + " from topic = " + message.getTopic());
                                    }
                                };


                        try {
                            client.subscribe(topic, true);                             // Subscribes the client to the topic
                            Thread.sleep(60000);                                        // Establishes the sleep time for the thread
                            client.disconnect();                                           // Disconnects the client
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        AWSMobileClient.getInstance().signOut();
                        break;
                    default:            // The default case
                        AWSMobileClient.getInstance().signOut();
                        break;
                }

            }

            @Override
            public void onError(Exception e) {
                System.out.println("Error!");
            }
        });

        */

    }

    @Override
    protected void onStart() {
        super.onStart();

        ProfileScreenFragment profileScreenFragment = new ProfileScreenFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.profileActivityContainer, profileScreenFragment).commit();

        notificationsButton = findViewById(R.id.NotifButton);
        notificationsButton.setOnClickListener(v->{
            NotificationsFragment notificationsFragment = new NotificationsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.profileActivityContainer, notificationsFragment).commit();
        });


        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v->{
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });
    }
}