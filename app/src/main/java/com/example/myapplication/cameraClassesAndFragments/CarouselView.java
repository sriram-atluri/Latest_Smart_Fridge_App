package com.example.myapplication.cameraClassesAndFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CarouselView extends RecyclerView.Adapter<CarouselView.CarouselViewHolder>{

    private List<CarouselItem> carouselItems;
    private ViewPager2 viewPager2;

    public CarouselView(List<CarouselItem> carouselItems, ViewPager2 viewPager2){
        this.carouselItems = carouselItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarouselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.setRoundedImageView(carouselItems.get(position));
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    class CarouselViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView roundedImageView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.carouselSlider);
        }

        void setRoundedImageView(CarouselItem carouselItem){
            roundedImageView.setImageBitmap(carouselItem.getImage());
        }
    }
}
