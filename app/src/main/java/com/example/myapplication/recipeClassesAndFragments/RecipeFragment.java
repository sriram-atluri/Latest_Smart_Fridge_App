package com.example.myapplication.recipeClassesAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryFragmentAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RecipeFragment extends Fragment {

    private TabLayout tabLayout;
    private TabItem ingredients;
    private TabItem recipes;
    private ViewPager2 recipeViewPager;
    private RecipeFragmentsFragmentAdapter recipeFragmentsFragmentAdapter;
    private Button findButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RecipesFragment.this)
                        .navigate(R.id.action_First3Fragment_to_Second3Fragment);
            }
        });*/

        tabLayout = view.findViewById(R.id.recipeTabs);
        ingredients = view.findViewById(R.id.ingredients_recipeFragment);
        recipes = view.findViewById(R.id.recipes_recipeFragment);

        recipeViewPager = view.findViewById(R.id.recipeViewPager);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        recipeFragmentsFragmentAdapter = new RecipeFragmentsFragmentAdapter(fm, getLifecycle());
        recipeViewPager.setAdapter(recipeFragmentsFragmentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                recipeViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    recipeFragmentsFragmentAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 1){
                    recipeFragmentsFragmentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recipeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



    }
}