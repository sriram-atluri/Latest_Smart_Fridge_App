package com.example.myapplication.recipeClassesAndFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RecipeFragmentsFragmentAdapter extends FragmentStateAdapter {

    public RecipeFragmentsFragmentAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle){
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new IngredientsFragment();
                break;
            case 1:
                fragment = new RecipesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
