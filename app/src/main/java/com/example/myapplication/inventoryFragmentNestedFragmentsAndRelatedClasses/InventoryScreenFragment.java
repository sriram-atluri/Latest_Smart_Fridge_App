package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryFragmentAdapter;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryItem;
import com.example.myapplication.recipeClassesAndFragments.IngredientItem;
import com.example.myapplication.recipeClassesAndFragments.IngredientsDBHelper;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class InventoryScreenFragment extends Fragment {

    private TabLayout fridgeComponents;
    ViewPager2 viewPager;
    private InventoryFragmentAdapter adapter;
    private TabItem pantry;
    private TabItem freezer;
    private TabItem fridge;
    private ImageButton inventoryAdd;
    private MQTTHandler mqttHandler;
    private Spinner spinner;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_inventoryscreen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IngredientsDBHelper ingredientsDBHelper = new IngredientsDBHelper(getContext());
        PantryDBHelper pantryDBHelper = new PantryDBHelper(getContext());
        FreezerDBHelper freezerDBHelper = new FreezerDBHelper(getContext());
        FridgeDBHelper fridgeDBHelper = new FridgeDBHelper(getContext());

        fridgeComponents = view.findViewById(R.id.smartFridgeComponents);
        //pantry = view.findViewById(R.id.pantry);
        //freezer = view.findViewById(R.id.freezer);
        //fridge = view.findViewById(R.id.fridge);
        viewPager = view.findViewById(R.id.viewPager);
        inventoryAdd = view.findViewById(R.id.inventoryAddButton);
        spinner = view.findViewById(R.id.spinner);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new InventoryFragmentAdapter(fm, getLifecycle());
        viewPager.setAdapter(adapter);


        inventoryAdd.setOnClickListener(v->{
            //Bundle bundle = new Bundle();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.inventory_request_form, null);
            builder.setView(customLayout);
            EditText inventoryRequest = customLayout.findViewById(R.id.inventoryChoice);
            //EditText storageArea = customLayout.findViewById(R.id.storageAreaChoice);
            EditText expiryDate = customLayout.findViewById(R.id.expiryEntry);
            Spinner spinner = customLayout.findViewById(R.id.spinner);
            ArrayAdapter<String> storageAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.storageAreaOptions));
            storageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(storageAdapter);
            builder.setPositiveButton("Cancel", (dialog, which) -> {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Confirm", (dialog, which) -> {
                switch(spinner.getSelectedItem().toString()) {
                    case "Pantry":
                        System.out.println("Entered 0");
                        pantryDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                        ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                        //mqttHandler.aws_subscribe();
                        mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Pantry", expiryDate.getText().toString());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);
                        break;
                    case "Fridge":
                        fridgeDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                        ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                        System.out.println("added to Helpers");
                        //mqttHandler.aws_subscribe();
                        mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Fridge", expiryDate.getText().toString());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //viewPager.setCurrentItem(2);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(1);
                        break;
                    case "Freezer":
                        freezerDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                        ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                        //mqttHandler.aws_subscribe();
                        mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Freezer", expiryDate.getText().toString());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //viewPager.setCurrentItem(1);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        Toast.makeText(getContext(), "Failed to add", Toast.LENGTH_SHORT).show();
                }
               /* System.out.println("Entered positive button");

                //spinner.setOnItemSelectedListener();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.err.println("Entered item select listener");
                        switch(parent.getSelectedItemPosition()){
                            case 0:
                                System.out.println("Entered 0");
                                pantryDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                                ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                                //mqttHandler.aws_subscribe();
                                mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Pantry", expiryDate.getText().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                viewPager.setAdapter(adapter);
                                viewPager.setCurrentItem(0);
                                break;
                            case 1:
                                fridgeDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                                ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                                System.out.println("added to Helpers");
                                //mqttHandler.aws_subscribe();
                                mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Fridge", expiryDate.getText().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //viewPager.setCurrentItem(2);
                                viewPager.setAdapter(adapter);
                                viewPager.setCurrentItem(1);
                                break;
                            case 2:
                                freezerDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                                ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                                //mqttHandler.aws_subscribe();
                                mqttHandler.add_inventory(inventoryRequest.getText().toString(), "Freezer", expiryDate.getText().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //viewPager.setCurrentItem(1);
                                viewPager.setAdapter(adapter);
                                viewPager.setCurrentItem(2);
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                /*
                if (inventoryRequest.getText().length() == 0 || storageArea.getText().length() == 0){
                    System.out.println("Error");
                }
                else {
                    System.out.println("Processing text");
                    switch(storageArea.getText().toString()){
                        case "Pantry":
                            pantryDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                            ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                            //mqttHandler.aws_subscribe();
                            mqttHandler.add_inventory(inventoryRequest.getText().toString(), storageArea.getText().toString(), expiryDate.getText().toString());
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            viewPager.setAdapter(adapter);
                            break;
                        case "Freezer":
                            freezerDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                            ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                            //mqttHandler.aws_subscribe();
                            mqttHandler.add_inventory(inventoryRequest.getText().toString(), storageArea.getText().toString(), expiryDate.getText().toString());
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //viewPager.setCurrentItem(1);
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(2);
                            break;
                        case "Fridge":
                            fridgeDBHelper.addData(inventoryRequest.getText().toString(), expiryDate.getText().toString());
                            ingredientsDBHelper.addItem(inventoryRequest.getText().toString());
                            System.out.println("added to Helpers");
                            //mqttHandler.aws_subscribe();
                            mqttHandler.add_inventory(inventoryRequest.getText().toString(), storageArea.getText().toString(), expiryDate.getText().toString());
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //viewPager.setCurrentItem(2);
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(1);
                            break;
                        default:
                            System.out.println("Non-existent");
                            break;
                    }
                }*/

            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        });


        fridgeComponents.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    adapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 1){
                    adapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 2){
                    adapter.notifyDataSetChanged();
                    //FridgeFragment fridgeFragment = new FridgeFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    adapter.notifyDataSetChanged();
                    //PantryFragment pantryFragment = new PantryFragment();
                }
                if (tab.getPosition() == 1){
                    adapter.notifyDataSetChanged();
                    //FreezerFragment freezerFragment = new FreezerFragment();
                }
                if (tab.getPosition() == 2){
                    adapter.notifyDataSetChanged();
                    //FridgeFragment fridgeFragment = new FridgeFragment();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    viewPager.getAdapter().notifyDataSetChanged();
                    //PantryFragment pantryFragment = new PantryFragment();
                }
                if (tab.getPosition() == 1){
                    adapter.notifyDataSetChanged();
                    //FreezerFragment freezerFragment = new FreezerFragment();
                }
                if (tab.getPosition() == 2){
                    adapter.notifyDataSetChanged();
                    //FridgeFragment fridgeFragment = new FridgeFragment();
                }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                fridgeComponents.selectTab(fridgeComponents.getTabAt(position));
            }
        });

        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InventoryScreenFragment.this)
                        .navigate(R.id.action_First8Fragment_to_Second8Fragment);
            }
        });*/
    }
}