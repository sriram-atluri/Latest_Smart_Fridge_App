package com.example.myapplication.ui.login;

import android.content.Context;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.example.myapplication.AWSCognito;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class SignUp extends AppCompatActivity {

    private EditText username, emailId, phoneNumber, password, repeatPassword, confirmationCode;
    private Button signUp, verify;
    private String userId;
    AWSCognito awsCognito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_up);
        setContentView(R.layout.activity_create_user_2);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Configures the EditText variables with the respective ids from the layout view


        /*
        verify.setOnClickListener(v->{
           // registerNewUser.confirmUser(userId, confirmationCode.getText().toString().replace("", ""));
        });*/
        awsCognito = new AWSCognito(getApplicationContext());
        initViewComponents();
    }

    private void initViewComponents(){
        username = findViewById(R.id.chosenUserName);
        emailId = findViewById(R.id.chosenEmailId);
        phoneNumber = findViewById(R.id.mobilePhoneNumber);
        password = findViewById(R.id.selectedPassword);
        repeatPassword = findViewById(R.id.repeatPassword);
        confirmationCode = findViewById(R.id.confirmationCode);


        signUp = findViewById(R.id.completeSignUp);
        verify = findViewById(R.id.completeVerification);

        signUp.setOnClickListener(v->{
            if(password.getText().toString().equals(repeatPassword.getText().toString())){
                Toast.makeText(getApplicationContext(), "You will be receiving a confirmation code shortly!", Toast.LENGTH_LONG).show();
                userId = username.getText().toString().replace(" ", "");
                awsCognito.addAttribute("name", userId);
                awsCognito.addAttribute("phone_number", phoneNumber.getText().toString().replace("", ""));
                awsCognito.addAttribute("email", emailId.getText().toString());
                awsCognito.signUpInBackground(userId, password.getText().toString());
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                awsCognito.confirmUser(userId, confirmationCode.getText().toString().replace(" ", ""));
                //finish();
            }
        });
    }
}