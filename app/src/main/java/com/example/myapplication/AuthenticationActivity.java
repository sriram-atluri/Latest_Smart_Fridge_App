package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import android.app.Application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.iot.AWSIotKeystoreHelper;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttSubscriptionStatusCallback;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.model.*;
import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.CreateThingResult;
import com.amazonaws.services.*;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;



import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;

//import java.io.File;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileSystems.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.*;
//import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
//import com.amazonaws.services.dynamodbv2.model.ReturnValue;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import com.example.myapplication.SampleUtil;
import com.amazonaws.services.iot.client.AWSIotMqttClient;

public class AuthenticationActivity extends AppCompatActivity {

    private final String TAG = AuthenticationActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        //System.out.println("Testing");
        getIntent();
        //startActivity(new Intent(this, ProfileScreen.class));
        // Provides the plugin for the authentication process to take place
        try {
            Amplify.addPlugin( new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }



        // Creates an AWSMobileClient object by fetching the Cognito Authorization plugin
        AWSMobileClient mobileClient = (AWSMobileClient) Amplify.Auth.getPlugin("awsCognitoAuthPlugin").getEscapeHatch();

        mobileClient.getInstance().signOut();

        mobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {            // The constructor that initalizes the application context for token creation
            @Override
            public void onResult(UserStateDetails result) {
                Log.i(TAG, "Trialboy " + result.getUserState().toString());
                /*
                MqttCallback callback = new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.i(TAG, cause.getMessage().toString() );
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        Log.i(TAG, "Message arrived " + ":" + topic.toString() + "," + message.getPayload().toString() );
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        try {
                            Log.i(TAG, token.getResponse().getPayload().toString());
                        } catch (Exception e) {
                            Log.i(TAG, "exception in delivery complete");
                        }
                    }
                };*/

                switch (result.getUserState()) {
                    case SIGNED_IN:
                        Log.e("INIT", "SIGNED_IN");
                        // showSignIn();
                        break;
                    case SIGNED_OUT:
                        System.out.println("before sign in");


                       showSignIn(mobileClient);
                       break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        break;
                }

            }


            @Override
            public void onError(Exception e) {
                System.out.println("Error!");
            }
        });



    }

    private void showSignIn(AWSMobileClient mobileClient) {
        try {
                  mobileClient.getInstance().showSignIn(this, SignInUIOptions.builder().nextActivity(MainActivity2.class).build());
           } catch (Exception e) {
            Log.e(TAG, e.toString());
        }


    }



}




