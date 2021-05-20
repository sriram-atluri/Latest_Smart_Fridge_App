package com.example.myapplication.profileSettingsClassesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.example.myapplication.AWSCognito;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.nex3z.notificationbadge.NotificationBadge;

public class ProfileScreenFragment extends Fragment {

    private Button settings;
    private Button logout;
    private ImageButton backButton;
    private NotificationBadge profScreenNotifBadge;
    private SharedPreferences prefs;
    public static final String myPreferences = "MyPrefs";

    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView phoneNo;
    private TextView address;

    Intent i;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profilescreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            getActivity().finish();
        });

        prefs = getActivity().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        String nameValue = prefs.getString("name", "");
        String ageValue = prefs.getString("age", "");
        String genderOutput = prefs.getString("gender", "");
        //String ageValue = prefs.getString("age", "");
        // phoneNumber = prefs.getString("phoneNo", "");
        String addressOutput = prefs.getString("city", "");

        name = view.findViewById(R.id.PersonNameAndAge);
        name.setText(nameValue + ", " + ageValue);

        gender = view.findViewById(R.id.GenderDisplay);
        gender.setText(genderOutput);
        //age.setText(ageValue);
        //phoneNo.setText(phoneNumber);

        address = view.findViewById(R.id.AddressDispaly);
        address.setText(addressOutput);

        settings = view.findViewById(R.id.account_settings);
        settings.setOnClickListener(v->{
            AccountSettingsFragment accountSettingsFragment = new AccountSettingsFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileActivityContainer, accountSettingsFragment).commit();
        });

        //AWSCognito awsCognito = new AWSCognito(getContext());
        logout = view.findViewById(R.id.logoutButton);
        logout.setOnClickListener(v->{
           AWSCognito awsCognito = new AWSCognito(getContext());
           awsCognito.userSignOut("sriram", "password123");
        });

    }
}
