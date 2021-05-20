package com.example.myapplication.firstFragments;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListingStyle extends RecyclerView.ItemDecoration {

    private int verticalSpaceHeight;    // The variable that is used to configure the spacing between recycler view items

    public ListingStyle(int verticalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = verticalSpaceHeight;
    }


}
