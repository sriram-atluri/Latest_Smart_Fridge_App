package com.example.myapplication.recipeClassesAndFragments;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.foodtimeperiods.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.widget.Toast;

public class RecipeTimeOfTheDay extends AppCompatActivity {

    private CardView breakfast;
    private CardView drinks;
    private CardView lunch;
    private CardView snacks;
    private CardView dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_time_of_the_day);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        breakfast = (CardView) findViewById(R.id.breakfast);
        breakfast.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testing breakfast button", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Breakfast.class);
            startActivity(i);
        });

        lunch = (CardView) findViewById(R.id.lunch);
        lunch.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testing lunch button", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Lunch.class);
            startActivity(i);
        });

        drinks = (CardView) findViewById(R.id.drinks);
        drinks.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testing drinks button", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Drinks.class);
            startActivity(i);
        });

        snacks = (CardView) findViewById(R.id.snacks);
        snacks.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testing snacks button", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Snacks.class);
            startActivity(i);
        });



        dinner = (CardView) findViewById(R.id.dinner);
        dinner.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testing dinner button", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Dinner.class);
            startActivity(i);
        });
    }
}