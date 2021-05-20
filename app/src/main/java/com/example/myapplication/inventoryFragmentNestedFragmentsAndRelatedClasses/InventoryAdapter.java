package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.cartFiles.ShoppingCartDBHelper;
import com.example.myapplication.recipeClassesAndFragments.IngredientsDBHelper;

import java.text.ParseException;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private Activity context;
    private List<InventoryItem> callListResponses;
    private MQTTHandler mqttHandler;

    public InventoryAdapter(Activity context, List<InventoryItem> callListResponses) {
        super();                                                            // Derives the methods from the parent class
        this.context = context;                                             // Sets the private "Context" variable equal to the context value passed into the constructor
        this.callListResponses = callListResponses;                         // Sets the callListResponses variable equal to the list values passed into the callListResponses variable passed into the constructor
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item_layout, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        final InventoryItem call = callListResponses.get(position);
        try {
            mqttHandler = MQTTHandler.getInstance(context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ShoppingCartDBHelper shoppingCartDBHelper = new ShoppingCartDBHelper(context);
        PantryDBHelper pantryDBHelper = new PantryDBHelper(context);
        FreezerDBHelper freezerDBHelper = new FreezerDBHelper(context);
        FridgeDBHelper fridgeDBHelper = new FridgeDBHelper(context);
        IngredientsDBHelper ingredientsDBHelper = new IngredientsDBHelper(context);
        holder.itemName.setText(call.getName());
        try {
            holder.expiryDate.setText(call.getExpiryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.transferToCart.setOnClickListener(v->{
            if(holder.transferToCart.isChecked()){
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Send to shopping cart?");
                builder.setNegativeButton("Yes", (dialog, which) -> {
                    callListResponses.remove(position);
                    shoppingCartDBHelper.addFoodItem(call.getName(), "0");

                    // Checks if the item is there and if its there, it deletes, benefit: no need of if-and-else conditions, because no Java error occurs
                    pantryDBHelper.deleteItem(pantryDBHelper.GetId(call.getName()));
                    fridgeDBHelper.deleteItem(fridgeDBHelper.GetId(call.getName()));
                    freezerDBHelper.deleteItem(freezerDBHelper.GetId(call.getName()));
                    ingredientsDBHelper.deleteItem(ingredientsDBHelper.GetId(call.getName()));      // Deletes item from the ingredients database
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mqttHandler.remove_from_inventory(call.getName());
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                });
                builder.setPositiveButton("No", (dialog, which) -> {
                    System.out.println("Cancelled");
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.black));
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.black));
            }
            //else {
            //    shoppingCartDBHelper.deleteItem(shoppingCartDBHelper.GetId(call.getName()));
            //}
        });
    }

    public void updateData(List callListResponses) {
        this.callListResponses = callListResponses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return callListResponses.size();
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView expiryDate;
        CheckBox transferToCart;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.inventoryName);
            expiryDate = itemView.findViewById(R.id.expiryDate);
            transferToCart = itemView.findViewById(R.id.transferToShoppingCart);
        }
    }
}