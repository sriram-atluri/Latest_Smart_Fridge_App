package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import android.app.AlertDialog;
import android.os.Build;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.recipeClassesAndFragments.IngredientsDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PantryFragment extends Fragment {

    private RecyclerView recyclerView;
    InventoryAdapter inventoryList;
    private ArrayList<InventoryItem> test;
    private FloatingActionButton refreshButton;
    private MQTTHandler mqttHandler;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //PantryDBHelper pantryDBHelper = new PantryDBHelper(getContext());
        recyclerView = view.findViewById(R.id.pantryRecyclerView);
        PantryDBHelper pantryDBHelper = new PantryDBHelper(getContext());
        IngredientsDBHelper ingredientsDBHelper = new IngredientsDBHelper(getContext());
        //refreshButton = view.findViewById(R.id.addToPantry);

       /* refreshButton.setOnClickListener(v-> {
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.inventory_request_form, null);
                    builder.setView(customLayout);
                    TextView title = customLayout.findViewById(R.id.request_title);
                    title.setText("Request - Add to Pantry");

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
                            pantryDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                            ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                                    //mqttHandler.aws_subscribe();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Pantry", expiryDate.getText().toString());
                            }
                        displayPantryItems();
                        });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));*/
            //displayPantryItems();
       // });
        //List<InventoryItem> pantryList = new ArrayList<>();
        //pantryList.add(updateData());
        //inventoryList = new InventoryAdapter(getActivity(), pantryList);
        displayPantryItems();
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //recyclerView.setAdapter(inventoryList);
    }

    //public InventoryItem updateData(){
    //    return new InventoryItem("Veggie Salad");
    //}
    public void displayPantryItems() {
        PantryDBHelper dbHelper = new PantryDBHelper(getActivity());
        test = new ArrayList<>(dbHelper.getItems());
        //System.out.println("Test name: " +test.get(0).getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        inventoryList = new InventoryAdapter(getActivity(), test);
       // inventoryList.updateData(test);
        recyclerView.setAdapter(inventoryList);
    }

}