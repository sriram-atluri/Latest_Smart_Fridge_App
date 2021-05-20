package com.example.myapplication.profileSettingsClassesAndFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.nex3z.notificationbadge.NotificationBadge;

public class AccountSettingsFragment extends Fragment {


    Intent i;

    private ImageButton backButton, homeButton, profileButton;

    private EditText name;
    private EditText age;
    private EditText phoneNo;
    private EditText emailId;
    private EditText physicalAddress;
    private EditText medicalCondition;
    private RadioGroup gender;
    private RadioButton genderMenuChoice;

    private String nameToToast, ageToToast, phoneToToast, emailToToast;

    public static final String myPreferences = "MyPrefs";
    private SharedPreferences prefs;                                    // While this can be declared here, initialization has to happen inside a method

    Switch testSwitch;

    private Button saveButton;
    ImageView testImage;

    private NotificationBadge postLoginScreenNotifBadge;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accountsettings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //String nameValue = prefs.getString("name", "");
        /*String ageValue = prefs.getString("age", "");
        String genderOutput = prefs.getString("gender", "");
        //String ageValue = prefs.getString("age", "");
        // phoneNumber = prefs.getString("phoneNo", "");
        String addressOutput = prefs.getString("address", "");

        name = view.findViewById(R.id.PersonNameAndAge);
        name.setText(nameValue + ", " + ageValue);*/

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

        prefs = getActivity().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            ProfileScreenFragment profileScreenFragment = new ProfileScreenFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileActivityContainer, profileScreenFragment).commit();
        });

        // The configuration when the user types in his/her name
        name = view.findViewById(R.id.PersonName);
        nameToToast = name.getText().toString();
        name.setText(nameToToast);
        name.setText(prefs.getString("name", ""));


        // The configuration when the user types in his/her age
        age = view.findViewById(R.id.PersonAge);
        ageToToast = age.getText().toString();
        age.setText(ageToToast);
        age.setText(prefs.getString("age", ""));

        // The configuration when the user types in his/her phone number
        phoneNo = view.findViewById(R.id.PersonPhoneNumber);
        //phoneNo.setOnClickListener(v -> {
            phoneToToast = phoneNo.getText().toString();
            phoneNo.setText(phoneToToast);
        //});
        phoneNo.setText(prefs.getString("phoneNo", ""));

        // The configuration when the user types in his/her email id
        emailId = view.findViewById(R.id.Email);
        //emailId.setOnClickListener(v -> {
            emailToToast = emailId.getText().toString();
            emailId.setText(emailToToast);
        //});
        emailId.setText(prefs.getString("email", ""));

        gender = view.findViewById(R.id.Gender);
        gender.clearCheck();

        gender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> genderMenuChoice = (RadioButton) group.findViewById(checkedId));

        physicalAddress = view.findViewById(R.id.physicalAddressInput);
        //physicalAddress.setOnClickListener(v->{
            String physAddressToToast = physicalAddress.getText().toString();
            physicalAddress.setText(physAddressToToast);
        //});
        physicalAddress.setText(prefs.getString("address", ""));

        medicalCondition = view.findViewById(R.id.medicalCondition);
        medicalCondition.setOnClickListener(v->{
            String medicalC = medicalCondition.getText().toString();
            medicalCondition.setText(medicalC);
        });

        // The configuration when the user toggles the switch
        //testImage = findViewById(R.id.testImageView);
        testSwitch = view.findViewById(R.id.switch1);
        testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Displaying personal information", Toast.LENGTH_SHORT).show();
                    //testImage.setImageDrawable(getDrawable(R.drawable.button));
                    prefs = getActivity().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                    medicalCondition.setText(prefs.getString("medical condition", ""));
                }
                else {
                    Toast.makeText(getContext(), "Hiding personal information", Toast.LENGTH_SHORT).show();
                    //testImage.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                    medicalCondition.setText("");
                }
            }
        });



        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v->{
            String nT = name.getText().toString();
            String aT = age.getText().toString();
            String pT = phoneNo.getText().toString();
            String eT = emailId.getText().toString();
            String gT = genderMenuChoice.getText().toString();
            String addressT = physicalAddress.getText().toString();
            String medicalT = medicalCondition.getText().toString();

            String city = addressT.split(",")[1];

            prefs = getActivity().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("name", nT);
            editor.putString("age", aT);
            editor.putString("email", eT);
            editor.putString("phoneNo", pT);
            editor.putString("gender", gT);
            editor.putString("address", addressT);
            editor.putString("city", city);
            editor.putString("medical condition", medicalT);
            editor.apply();

        });


    }
}
