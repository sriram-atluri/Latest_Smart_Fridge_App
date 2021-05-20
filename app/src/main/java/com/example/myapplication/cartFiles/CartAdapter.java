package com.example.myapplication.cartFiles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;


// Provides the bridge between RecyclerView and underlying data
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Activity context;
    private List<CartItem> callListResponses;

    public CartAdapter(Activity context, List<CartItem> callListResponses) {
        super();                                                            // Derives the methods from the parent class
        notifyDataSetChanged();
        this.context = context;                                             // Sets the private "Context" variable equal to the context value passed into the constructor
        this.callListResponses = callListResponses;                         // Sets the callListResponses variable equal to the list values passed into the callListResponses variable passed into the constructor
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);

        return new CartViewHolder(itemView);
    }

    // This method could be subject to change
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final CartItem call = callListResponses.get(position);
        ShoppingCartDBHelper shoppingCartDBHelper = new ShoppingCartDBHelper(context);
        holder.itemName.setText(call.getFoodName());
        holder.increment.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantity.getText().toString()) + 1;
            if (quantity > 5) {
                Toast.makeText(context, "Cannot be more than 5", Toast.LENGTH_SHORT).show();
            }
            else {
                String stringifiedQuantity = Integer.toString(quantity);
                shoppingCartDBHelper.updateItem(Integer.toString(shoppingCartDBHelper.GetId(call.getFoodName())), call.getFoodName(), stringifiedQuantity);
                holder.quantity.setText(stringifiedQuantity);
            }
        });
        holder.decrement.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantity.getText().toString()) - 1;
            if (quantity < 1){
                Toast.makeText(context, "Sorry, cannot be negative", Toast.LENGTH_SHORT).show();
            }
            else {
                String stringifiedQuantity = Integer.toString(quantity);
                shoppingCartDBHelper.updateItem(Integer.toString(shoppingCartDBHelper.GetId(call.getFoodName())), call.getFoodName(), stringifiedQuantity);
                holder.quantity.setText(stringifiedQuantity);
            }
        });
        holder.deleteButton.setOnClickListener(v -> {

            callListResponses.remove(position);             // Removes the item at the position
            shoppingCartDBHelper.deleteItem(shoppingCartDBHelper.GetId(call.getFoodName()));
            notifyDataSetChanged();                         // Updates the view holder
        });
    }


    @Override
    public int getItemCount() {
        return callListResponses.size();
    }

    // Inner class for a customized ViewHolder
    class CartViewHolder extends RecyclerView.ViewHolder {
            TextView itemName, quantity;
            Button increment, decrement;
            ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.foodImage);
            //imageView.setImageResource(R.drawable.orange_juice_glass);
            itemName = (TextView) itemView.findViewById(R.id.FoodName);
            quantity = itemView.findViewById(R.id.quantity);
            increment = itemView.findViewById(R.id.increase);
            decrement = itemView.findViewById(R.id.decrease);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
