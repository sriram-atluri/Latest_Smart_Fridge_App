package com.example.myapplication.cameraClassesAndFragments;

import android.graphics.Bitmap;

public class CarouselItem {

    private Bitmap image;
    private int normalImage;

    public CarouselItem(Bitmap image){
        this.image = image;
    }

    public CarouselItem(int normalImage){
        this.normalImage = normalImage;
    }

    public Bitmap getImage() {
        return image;
    }
}
