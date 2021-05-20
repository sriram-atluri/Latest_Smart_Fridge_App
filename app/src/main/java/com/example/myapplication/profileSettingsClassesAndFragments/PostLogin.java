package com.example.myapplication.profileSettingsClassesAndFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.nex3z.notificationbadge.NotificationBadge;


public class PostLogin extends AppCompatActivity {


    Intent i;

    private ImageButton backButton, homeButton, profileButton;

    private EditText name;
    private EditText age;
    private EditText phoneNo;
    private EditText emailId;
    private EditText physicalAddress;
    private RadioGroup gender;
    private RadioButton genderMenuChoice;

    private String nameToToast, ageToToast, phoneToToast, emailToToast;

    public static final String myPreferences = "MyPrefs";
    private SharedPreferences prefs;

    Switch testSwitch;

    private Button saveButton;
    ImageView testImage;

    private NotificationBadge postLoginScreenNotifBadge;

    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            name.setText(savedInstanceState.getString("name"));
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        // UNCOMMENT LATER
        //postLoginScreenNotifBadge = findViewById(R.id.NotifBadge);
        //postLoginScreenNotifBadge.setNumber(1);

        profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(v->{
            i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });
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

        backButton = findViewById(R.id.backButtonSpecial);
        backButton.setOnClickListener(v->{
            Intent i = new Intent(this, ProfileSettingsActivity.class);
            startActivity(i);
        });

        // The configuration when the user types in his/her name
        name = findViewById(R.id.PersonName);
        /*name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameToToast = name.getText().toString();
                if (savedInstanceState != null){
                    String value = savedInstanceState.getString("name");
                }
                name.setText(nameToToast);
                Toast.makeText(PostLogin.this, nameToToast, Toast.LENGTH_SHORT).show();
            }
        });*/

        // The configuration when the user types in his/her age
        age = findViewById(R.id.PersonAge);
        /*age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageToToast = age.getText().toString();
                Toast.makeText(PostLogin.this, ageToToast, Toast.LENGTH_SHORT).show();
            }
        });*/

        // The configuration when the user types in his/her phone number
        phoneNo = findViewById(R.id.PersonPhoneNumber);
        /*phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneToToast = phoneNo.getText().toString();
                Toast.makeText(PostLogin.this, phoneToToast, Toast.LENGTH_SHORT).show();
            }
        });*/

        // The configuration when the user types in his/her email id
        emailId = findViewById(R.id.Email);
        /*emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailToToast = emailId.getText().toString();
                Toast.makeText(PostLogin.this, emailToToast, Toast.LENGTH_SHORT).show();
            }
        });*/

        gender = findViewById(R.id.Gender);
        gender.clearCheck();

        gender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> genderMenuChoice = (RadioButton) group.findViewById(checkedId));

        physicalAddress = findViewById(R.id.physicalAddressInput);

        // The configuration when the user toggles the switch
        //testImage = findViewById(R.id.testImageView);
        testSwitch = findViewById(R.id.switch1);
        testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PostLogin.this, "Displaying personal information", Toast.LENGTH_SHORT).show();
                    //testImage.setImageDrawable(getDrawable(R.drawable.button));

                }
                else {
                    Toast.makeText(PostLogin.this, "Hiding personal information", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    age.setText("");
                    phoneNo.setText("");
                    //testImage.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                }
            }
        });



        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v->{
            String nT = name.getText().toString();
            String aT = age.getText().toString();
            String pT = phoneNo.getText().toString();
            String eT = emailId.getText().toString();
            String gT = genderMenuChoice.getText().toString();
            String addressT = physicalAddress.getText().toString();

            String city = addressT.split(",")[1];

            prefs = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("name", nT);
            editor.putString("age", aT);
            editor.putString("email", eT);
            editor.putString("phoneNo", pT);
            editor.putString("gender", gT);
            editor.putString("address", city);
            editor.apply();

        });




    }

    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name.setText(savedInstanceState.getString("name"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name.getText().toString());
    }*/
}
