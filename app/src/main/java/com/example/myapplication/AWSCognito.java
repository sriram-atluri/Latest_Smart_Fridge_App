package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.activityClasses.CameraActivity;
import com.example.myapplication.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class AWSCognito {

    // Pool and client credentials
    private String poolID = "us-west-2_NgWcyJPfu";
    private String clientID = "3405dqrmjes3j39lo0pgopnacc";
    private String clientSecret = null;

    // User pool settings
    private CognitoUserPool userPool;
    private Regions awsRegion = Regions.US_WEST_2;
    private CognitoUserAttributes userAttributes;
    private Context appContext;

    private String userPassword;

    AWSMobileClient awsMobileClient;

    // Copy constructor
    public AWSCognito(Context context){
        appContext = context;
        userPool = new CognitoUserPool(context, this.poolID, this.clientID, this.clientSecret, this.awsRegion);
        userAttributes = new CognitoUserAttributes();
    }

    public void signUpInBackground(String userId, String password){
        userPool.signUpInBackground(userId, password, this.userAttributes, null, signUpCallback);
        //userPool.signUp(userId, password, this.userAttributes, null, signUpCallback);
    }

    SignUpHandler signUpCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser cognitoUser, SignUpResult signUpResult) {
            // Sign-up was successful
            Log.d(TAG, "Sign-up success");
            Toast.makeText(appContext,"Sign-up success", Toast.LENGTH_LONG).show();
            // Check if this user (cognitoUser) needs to be confirmed
            if(!signUpResult.getUserConfirmed()) {
                // This user must be confirmed and a confirmation code was sent to the user
                // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                // Get the confirmation code from user
            }
            else {
                Toast.makeText(appContext,"Error: User Confirmed before", Toast.LENGTH_LONG).show();
                // The user has already been confirmed
            }
        }
        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(appContext,"Sign-up failed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up failed: " + exception);
        }
    };


    public void confirmUser(String userId, String code){
        CognitoUser cognitoUser =  userPool.getUser(userId);
        cognitoUser.confirmSignUpInBackground(code,false, confirmationCallback);
        //cognitoUser.confirmSignUp(code,false, confirmationCallback);
    }


    // Callback handler for confirmSignUp API
    GenericHandler confirmationCallback = new GenericHandler() {

        @Override
        public void onSuccess() {
            // User was successfully confirmed
            Toast.makeText(appContext,"User Confirmed", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.

        }
    };


    public void addAttribute(String key, String value){
        userAttributes.addAttribute(key, value);
    }


    public void userLogin(String userId, String password){
        CognitoUser cognitoUser =  userPool.getUser(userId);
        this.userPassword = password;
        cognitoUser.signOut();
        cognitoUser.getSessionInBackground(authenticationHandler);
    }

    public void userSignOut(String userId, String userPassword){
        System.out.println("Entering sign out page");
        CognitoUser cognitoUser =  userPool.getUser(userId);
        System.out.println(cognitoUser.getUserId());
        this.userPassword = userPassword;
        cognitoUser.signOut();
        appContext.startActivity(new Intent(appContext, LoginActivity.class));
    }

    public void forgotPassword(String userName){
        CognitoUser cognitoUser = userPool.getUser(userName);
        //cognitoUser.forgotPassword();
    }

    // Callback handler for the sign-in process
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            Toast.makeText(appContext,"Completed authentication challenge", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(appContext,"Sign in success", Toast.LENGTH_LONG).show();


            AWSMobileClient.getInstance().initialize(appContext, new Callback<UserStateDetails>(){
                @Override
                public void onResult(UserStateDetails result){
                    try {
                        MQTTHandler.getInstance(appContext);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    appContext.startActivity(new Intent(appContext, MainActivity2.class));
                    //SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(appContext, SignInUI.class);
                    //signin.login(AuthenticatorActivity.this, MainNavigationActivity.class).execute();
                    showSignIn();
                }
                private void showSignIn() {
                    System.out.println("The username is: " + userSession.getUsername());
                    // This was null because you needed to pass in username into getUser()
//                    System.out.println("The username is: " + userPool.getUser(userSession.getUsername()).getUserId());
                }

                @Override
                public void onError(Exception e) {

                }
            });

            /*
            try {
                AWSMobileClient.getInstance().showSignIn((Activity) appContext,  SignInUIOptions.builder().nextActivity(MainActivity2.class).build());
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }
        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            // Multi-factor authentication is required; get the verification code from user
            //multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
            // Allow the sign-in process to continue
            //multiFactorAuthenticationContinuation.continueTask();
        }
        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(appContext,"Sign in Failure", Toast.LENGTH_LONG).show();
        }
    };
}