package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.recipeClassesAndFragments.IngredientsDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FridgeFragment extends Fragment {

    private RecyclerView recyclerView;
    private InventoryAdapter inventoryList;
    private ArrayList<InventoryItem> test;
    private MQTTHandler mqttHandler;
    private FloatingActionButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fridgeRecyclerView);

        FridgeDBHelper fridgeDBHelper = new FridgeDBHelper(getContext());
        IngredientsDBHelper ingredientsDBHelper = new IngredientsDBHelper(getContext());
        //addButton = view.findViewById(R.id.addToFridge);

        /*addButton.setOnClickListener(v-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.inventory_request_form, null);
            builder.setView(customLayout);
            TextView title = customLayout.findViewById(R.id.request_title);
            title.setText("Request - Add to Fridge");
            EditText inventoryRequest = customLayout.findViewById(R.id.inventoryChoice);
            EditText expiryDate = customLayout.findViewById(R.id.expiryEntry);

            builder.setPositiveButton("Cancel", (dialog, which) -> {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Confirm", (dialog, which) -> {
                System.out.println("Entered positive button");
                if (inventoryRequest.getText().length() == 0) {
                    System.out.println("Error");
                } else {
                    fridgeDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                    ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                    //mqttHandler.aws_subscribe();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Fridge", expiryDate.getText().toString());
                }
                displayFridgeItems();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        });*/

        displayFridgeItems();
    }

    public void displayFridgeItems(){
        FridgeDBHelper dbHelper = new FridgeDBHelper(getActivity());
        test = new ArrayList<>(dbHelper.getItems());
        //System.out.println("Test name: " + test.get(0).getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        inventoryList = new InventoryAdapter(getActivity(), test);
        recyclerView.setAdapter(inventoryList);
    }
}