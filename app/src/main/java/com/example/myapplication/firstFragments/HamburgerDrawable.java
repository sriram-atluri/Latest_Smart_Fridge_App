package com.example.myapplication.firstFragments;

import android.content.Context;
import android.graphics.Canvas;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

import com.example.myapplication.R;

public class HamburgerDrawable extends DrawerArrowDrawable {
    /**
     * @param context used to get the configuration for the drawable from
     */
    public HamburgerDrawable(Context context) {
        super(context);
        setColor(context.getColor(R.color.red));
    }

    public void draw(Canvas canvas){
        super.draw(canvas);

        setBarLength(100.0f);
        setBarThickness(50.0f);
        setGapSize(7.0f);
    }
}
