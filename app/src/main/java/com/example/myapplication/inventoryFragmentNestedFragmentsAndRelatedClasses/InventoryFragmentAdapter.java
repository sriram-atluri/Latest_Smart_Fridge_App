package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InventoryFragmentAdapter extends FragmentStateAdapter {

    public InventoryFragmentAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle){
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fg = null;
        switch (position)
        {
            case 0:
                fg = new PantryFragment();
                break;
            case 1:
                fg = new FridgeFragment();
                break;
            case 2:
                fg =  new FreezerFragment();
                break;
        }
        return fg;
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
