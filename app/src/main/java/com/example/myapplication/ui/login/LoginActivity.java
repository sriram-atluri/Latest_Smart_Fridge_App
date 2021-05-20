package  com.example.myapplication.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.myapplication.AWSCognito;
import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;

import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button btnLogin;
    private Button signUp;
    private LoginViewModel loginViewModel;
    private ConstraintLayout constraintLayout;
    private GifImageView gifImageView;
    MQTTHandler mqttHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
        // .get(LoginViewModel.class);
        getSupportActionBar().hide();

        initViewComponents();
    }

    private void initViewComponents(){
        //txtNotAccount = findViewById(R.id.txtNotAccount);
        //txtForgetPass= findViewById(R.id.txtForgetPass);
        username = findViewById(R.id.username);     // Retrieves the id of the username TextEdit in the layout file
        password = findViewById(R.id.password);     // Retrieves the id of the password TextEdit in the layout file
        btnLogin = findViewById(R.id.login);        // Retrieves the id of the login button in the layout file
        signUp = findViewById(R.id.signup_button);
        constraintLayout = findViewById(R.id.container);
        gifImageView = findViewById(R.id.firstAnimation);

        /*
        txtNotAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });*/

        btnLogin.setOnClickListener(view -> {
            AWSCognito authentication = new AWSCognito(LoginActivity.this);
            authentication.userLogin(username.getText().toString(), password.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putString(username.getText().toString(), password.getText().toString());

            hideKeybaord(view);
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);
            //constraintLayout.setForeground(getDrawable(R.color.white));
            gifImageView.setImageResource(R.drawable.animated_simpleat_logo_new);
        });

        signUp.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        /*
        try {
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
        }

        mqttHandler.enable_temp();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fridgeTemperatureValue = mqttHandler.get_fridge_temp();
        //String fridgeTemperatureValue = "0";
        mqttHandler.disable_temp();

        Bundle bundle = new Bundle();                                                           // Creates a bundle object o pass data values to a fragment or activity
        bundle.putString("fridgeTemp", fridgeTemperatureValue);                                 // The fridge temperature value is mapped to the fridgeTemp ke
        Intent i = new Intent(this, MainActivity2.class);*/

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

}