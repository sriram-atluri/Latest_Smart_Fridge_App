package com.example.myapplication.firstFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.InventoryScreen;
import com.example.myapplication.BarcodeScanningClasses.QRAndBarcodeScanActivity;
import com.example.myapplication.R;
import com.example.myapplication.activityClasses.AboutPage;
import com.example.myapplication.activityClasses.CameraActivity;
import com.example.myapplication.activityClasses.CartActivity;
import com.example.myapplication.activityClasses.ContactsPage;
import com.example.myapplication.activityClasses.HelpPage;
import com.example.myapplication.recipeClassesAndFragments.RecipeScreen;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private ImageButton recipeButton, inventoryScreenButton, cameraScreenButton, shoppingCartScreenButton;
    private Button QRScan;
    private TextView freezerTemperature, fridgeTemperature;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    String myValue;
    //MQTTHandler mqttHandler = new MQTTHandler(getContext());    // Creates an instance of the MQTTHandler to handle the messaging subscriptions
    //boolean subscribed = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        /*
        if (!subscribed) {
            mqttHandler.aws_subscribe();                                // Calls the aws_subscribe function
            subscribed = true;
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String fridgeTemperatureValue = mqttHandler.get_fridge_temp();

        System.out.println("Temperature is " + fridgeTemperatureValue);*/

        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_home_icon, container, false);
        /*
        recipeButton = (ImageButton) getView().findViewById(R.id.recipeButton);
        inventoryScreenButton = (ImageButton) getView().findViewById(R.id.inventoryScreen);
        cameraScreenButton = (ImageButton) getView().findViewById(R.id.cameraScreenDefault);
        shoppingCartScreenButton = (ImageButton) getView().findViewById(R.id.shoppingCart);


        recipeButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), RecipeScreen1.class);                           // Creates an intent to indicate that the user would like to go from "this" menu to the "RecipeScreen1" menu
            startActivity(i);                                                                         // Enables the migration from the origin menu to the destination menu
        });

        inventoryScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), InventoryScreen.class);
            startActivity(i);
        });

        cameraScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CameraScreen1.class);
            startActivity(i);
        });

        shoppingCartScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CartActivity.class);
            startActivity(i);
        });


        QRScan = getView().findViewById(R.id.QR_SCAN);
        QRScan.setOnClickListener(v->{
            startActivity(new Intent(getActivity(),  QRAndBarcodeScanActivity.class));
        });*/
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/

        freezerTemperature = getView().findViewById(R.id.freezerTemperatureDisplay);
        fridgeTemperature = getView().findViewById(R.id.fridgeTemperatureDisplay);
        recipeButton = (ImageButton) getView().findViewById(R.id.recipeButton);
        inventoryScreenButton = (ImageButton) getView().findViewById(R.id.inventoryScreen);
        cameraScreenButton = (ImageButton) getView().findViewById(R.id.cameraScreenDefault);
        shoppingCartScreenButton = (ImageButton) getView().findViewById(R.id.shoppingCart);
        fridgeTemperature = getView().findViewById(R.id.fridgeTemperatureDisplay);

        //if (!subscribed) {
        //    mqttHandler.aws_subscribe();                                // Calls the aws_subscribe function
        //    subscribed = true;
        //}
        //String fridgeTemperatureValue = mqttHandler.get_fridge_temp();

        //fridgeTemperature.setText(fridgeTemperatureValue);       // Sets the fridgeTemperature TextView to display the value obtained from the get_fridge_temp function

        //String fridgeTemperatureValue = mqttHandler.get_fridge_temp();

        //System.out.println("Temperature is " + fridgeTemperatureValue);
      //  try {
        myValue = getArguments().getString("fridgeTemp");
        //    System.err.println(myValue);
       // } catch (NullPointerException e){
        //    e.printStackTrace();
       // }
        fridgeTemperature.setText(myValue);


        //fridgeTemperature.setText("35\u00B0F");
        recipeButton.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), RecipeScreen.class);                           // Creates an intent to indicate that the user would like to go from "this" menu to the "RecipeScreen1" menu
            startActivity(i);                                                                         // Enables the migration from the origin menu to the destination menu
        });

        inventoryScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), InventoryScreen.class);
            startActivity(i);
        });

        cameraScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CameraActivity.class);
            startActivity(i);
        });

        shoppingCartScreenButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CartActivity.class);
            startActivity(i);
        });



        Toolbar toolbar = getView().findViewById(R.id.HamburgerToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView = (NavigationView) getView().findViewById(R.id.nav_view);
        drawerLayout = getView().findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        //toggle.setDrawerArrowDrawable(new HamburgerDrawable(getContext()));
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);

        drawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                /*switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "Home is working", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity2.this, AboutPage.class);
                        startActivity(i);
                        //drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_gallery:
                        Toast.makeText(getApplicationContext(), "Going to contacts", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity2.this, ContactsPage.class);
                        startActivity(i);
                    case R.id.nav_slideshow:
                        Toast.makeText(getApplicationContext(), "Going to help page", Toast.LENGTH_LONG).show();
                        i = new Intent(MainActivity2.this, HelpPage.class);
                        startActivity(i);
                }*/
                if (item.getItemId() == R.id.nav_home){
                    Toast.makeText(getContext(), "Home is working", Toast.LENGTH_LONG).show();
                    //i = new Intent(MainActivity2.this, QRAndBarcodeScanActivity.class);
                    i = new Intent(getActivity(), AboutPage.class);
                    startActivity(i);
                }
                else if (item.getItemId() == R.id.nav_gallery){
                    Toast.makeText(getContext(), "Going to contacts", Toast.LENGTH_LONG).show();
                    i = new Intent(getActivity(), ContactsPage.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getContext(), "Going to help page", Toast.LENGTH_LONG).show();
                    i = new Intent(getActivity(), HelpPage.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}