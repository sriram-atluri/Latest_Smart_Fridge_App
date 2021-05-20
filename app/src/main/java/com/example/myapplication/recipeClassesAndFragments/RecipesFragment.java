package com.example.myapplication.recipeClassesAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.firstFragments.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    List<RecipeItem> recipeItemList;
    private Button loadRecipes;
    String receivingMessage;
    RecipePageDBHelper recipePageDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.recipeRecyclerView);

        displayRecipes();

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recipePageDBHelper.deleteAll();

    }

    public void displayRecipes(){
        recipePageDBHelper = new RecipePageDBHelper(getActivity());
        recipeItemList = recipePageDBHelper.getItems();
        System.out.println("Array transcript: " + recipePageDBHelper.getItems());
        //recipeItemList = new ArrayList<>();
        System.out.println("Size of recipe list is : " + recipeItemList.size());
        for (int i = 0; i < recipeItemList.size(); i++) {
            System.out.println(recipeItemList.get(i).getFoodName());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recipeAdapter = new RecipeAdapter(getActivity(), recipeItemList);
        recyclerView.setAdapter(recipeAdapter);
    }
}
