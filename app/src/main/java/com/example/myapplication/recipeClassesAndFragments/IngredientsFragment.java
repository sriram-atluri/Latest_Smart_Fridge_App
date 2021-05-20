package com.example.myapplication.recipeClassesAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.firstFragments.NotificationAdapter;
import com.example.myapplication.firstFragments.NotificationDBHelper;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryAdapter;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private RecyclerView recyclerView;                      // Creates a RecyclerView object
    private IngredientAdapter ingredientAdapter;            // Creates an IngredientAdapter object
    private Button findButton;
    List<IngredientItem> ingredientItemList;                        // Creates an ArrayList object
    MQTTHandler mqttHandler;
    private ViewPager2 viewPager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mqttHandler = null;
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //mqttHandler.aws_subscribe();
        ViewPager2 viewPager2 = getActivity().findViewById(R.id.recipeViewPager);
        RecipePageDBHelper recipePageDBHelper = new RecipePageDBHelper(getContext());
        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);

        displayIngredients();

        findButton = getView().findViewById(R.id.findButton);
        findButton.setOnClickListener(v -> {

            recipePageDBHelper.deleteAll();
            mqttHandler.request_recipe(ingredientAdapter.listofIngredients().toArray(new String[0]));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //for (int i=0; i<mqttHandler.get_recipe_titles().length; i++){
                //System.out.println("\nTitle of Recipe: " + mqttHandler.get_recipe_titles()[0]);
            /*for (int j=0; j<recipePageDBHelper.getItems().size(); j++){
                System.out.println("Deletion iteration " + j);
                // Iteratively deletes the Item in each index position
                id = recipePageDBHelper.GetId(recipePageDBHelper.getItems().get(j).getFoodName());
                recipePageDBHelper.deleteItem(id);
            }*/
            //try {
           //     Thread.sleep(1000);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            for (int i=0; i < mqttHandler.get_recipe_titles().length; i++){

                System.out.println("\nName of Recipe: " + mqttHandler.get_recipe_titles()[0]);
                System.out.println("\nURL: " + mqttHandler.get_recipe_urls()[0]);
                System.out.println("\nTime: " + mqttHandler.get_recipe_times()[0]);

                 //Loops through the recipePageDBHelper items ArrayList
                if (mqttHandler.get_recipe_titles().length == 0) {
                    recipePageDBHelper.deleteAll();
                    break;
                }
                recipePageDBHelper.addItem(mqttHandler.get_recipe_titles()[i], mqttHandler.get_recipe_urls()[i], mqttHandler.get_recipe_times()[i]);
            }
            viewPager2.setCurrentItem(1);
        });

    }


    public void displayIngredients(){
        IngredientsDBHelper dbHelper = new IngredientsDBHelper(getActivity());
        //dbHelper.deleteAll();
        ingredientItemList = new ArrayList<>(dbHelper.getItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientItemList);
        recyclerView.setAdapter(ingredientAdapter);
    }


}
