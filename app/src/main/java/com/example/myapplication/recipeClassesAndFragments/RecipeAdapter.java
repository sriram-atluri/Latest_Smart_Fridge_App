package com.example.myapplication.recipeClassesAndFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Activity context;
    private List<RecipeItem> callListResponses;

    public RecipeAdapter(Activity context, List<RecipeItem> callListResponses) {
        super();                                                            // Derives the methods from the parent class
        this.context = context;                                             // Sets the private "Context" variable equal to the context value passed into the constructor
        this.callListResponses = callListResponses;                         // Sets the callListResponses variable equal to the list values passed into the callListResponses variable passed into the constructor
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        final RecipeItem call = callListResponses.get(position);
        holder.itemName.setText(call.getFoodName());
        holder.url.setOnClickListener(v->{
            //System.out.println("URL transcript: " + call.getUrl());
            goToUrl(call.getUrl());
        });
        holder.time.setText(call.getTime());
    }

    public void goToUrl(String website){
        Uri uri = Uri.parse(website);
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public int getItemCount() {
        return callListResponses.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageView url;
        TextView time;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.recipeName);
            url = itemView.findViewById(R.id.recipeURL);
            time = (TextView) itemView.findViewById(R.id.recipeTime);
        }
    }
}
