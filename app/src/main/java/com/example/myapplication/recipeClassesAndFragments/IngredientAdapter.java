package com.example.myapplication.recipeClassesAndFragments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryAdapter;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private Activity context;
    private List<IngredientItem> callListResponses;
    //MatchRecipe matchRecipe;
    // Creates an arrayList for selectedIngredients
    ArrayList<String> selectedIngredients;

    public IngredientAdapter(Activity context, List<IngredientItem> callListResponses) {
        super();                                                            // Derives the methods from the parent class
        this.context = context;                                             // Sets the private "Context" variable equal to the context value passed into the constructor
        this.callListResponses = callListResponses;                         // Sets the callListResponses variable equal to the list values passed into the callListResponses variable passed into the constructor
        //this.matchRecipe = matchRecipe;
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        // Locates the position of the item
        final IngredientItem call = callListResponses.get(position);


        // Retrieves the ingredient name
        holder.itemName.setText(call.getIngredientName());

        // Implements the functionality for the CheckBox attribute
        holder.select.setOnClickListener(v->{
            if (holder.select.isChecked()) {
                System.out.println("Checked");
                selectedIngredients.add(call.getIngredientName());
                System.out.println("Added");
            }
            else {
                System.out.println("Unchecked");
                selectedIngredients.remove(call.getIngredientName());
                System.out.println("Removed");
            }
            //matchRecipe.sendIngredients(selectedIngredients);
        });
        //matchRecipe.fetchRecipes(selectedIngredients);
    }

    @Override
    public int getItemCount() {
        return callListResponses.size();
    }

    public List<String> listofIngredients(){
        System.out.println(selectedIngredients.size());
        return selectedIngredients;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        CheckBox select;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.ingredientName);
            select = itemView.findViewById(R.id.checkBox);
            selectedIngredients = new ArrayList<>();
        }
    }

}
