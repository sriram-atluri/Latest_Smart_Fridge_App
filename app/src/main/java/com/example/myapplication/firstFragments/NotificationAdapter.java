package com.example.myapplication.firstFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.recipeClassesAndFragments.RecipePageDBHelper;
import com.example.myapplication.recipeClassesAndFragments.RecipeScreen;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Activity context;
    private List<NotificationItem> callListResponses;
    private NotificationDBHelper notificationDBHelper;
    ArrayList<NotificationItem> notifications;
    private MQTTHandler mqttHandler;

    public NotificationAdapter(Activity context, List<NotificationItem> callListResponses) {
        super();                                                            // Derives the methods from the parent class
        this.context = context;                                             // Sets the private "Context" variable equal to the context value passed into the constructor
        this.callListResponses = callListResponses;                         // Sets the callListResponses variable equal to the list values passed into the callListResponses variable passed into the constructor
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.notification_item_layout, parent, false);

        return new NotificationAdapter.NotificationViewHolder(itemView);
    }

    // This method could be subject to change
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final NotificationItem call = callListResponses.get(position);
        notificationDBHelper = new NotificationDBHelper(context);
        RecipePageDBHelper recipePageDBHelper = new RecipePageDBHelper(context);
        //holder.status.setText(call.getName());
        holder.notificationText.setText(call.getName());

        try {
            mqttHandler = MQTTHandler.getInstance(context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Configures the color settings
        if (call.getStatus().equals("Expiring in 2 days")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#66BB6A"));
            holder.status.setText(call.getStatus());
        }
        else if (call.getStatus().equals("Expiring in 1 day")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF176"));
            holder.status.setText(call.getStatus());
        }
        else if (call.getStatus().equals("Expiring Today")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E57373"));
            holder.status.setText("Expiring Today");
        }
        else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.status.setText("Running low");
        }
        holder.time.setText(call.getTime());
        holder.recipeButton.setOnClickListener(v->{
                if (!recipePageDBHelper.getItems().isEmpty()) {
                    recipePageDBHelper.deleteAll();
                }
                mqttHandler.request_recipe(call.getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i=0; i < mqttHandler.get_recipe_titles().length; i++){

                    //System.out.println("\nName of Recipe: " + mqttHandler.get_recipe_titles()[0]);
                    //System.out.println("\nURL: " + mqttHandler.get_recipe_urls()[0]);
                    //System.out.println("\nTime: " + mqttHandler.get_recipe_times()[0]);

                    //Loops through the recipePageDBHelper items ArrayList
                    if (mqttHandler.get_recipe_titles().length == 0) {
                        //recipePageDBHelper.deleteAll();
                        break;
                    }
                    recipePageDBHelper.addItem(mqttHandler.get_recipe_titles()[i], mqttHandler.get_recipe_urls()[i], mqttHandler.get_recipe_times()[i]);
                    //Intent j = new Intent(context, RecipeScreen.class);

                }
               System.out.println("Items received" + recipePageDBHelper.getItems());
               Intent i = new Intent(context, RecipeScreen.class);
               context.startActivity(i);
            });
        holder.deleteButton.setOnClickListener(v->{
            callListResponses.remove(position);
            notificationDBHelper.deleteItem(notificationDBHelper.GetId(call.getName()));
            notifyDataSetChanged();
            notifyItemRemoved(position);
        });

    }


    @Override
    public int getItemCount() {
        if (callListResponses == null) {
            return 0;
        }
        else {
            return callListResponses.size();
        }
    }



    // Inner class for a customized ViewHolder
    class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageButton deleteButton;
        TextView notificationText;
        TextView status;
        TextView time;
        ImageView notificationBorder;
        Button recipeButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.notificationCard);
            notificationText = itemView.findViewById(R.id.notificationItem);
            //addButton = itemView.findViewById(R.id.AddButton);
            recipeButton = itemView.findViewById(R.id.goToRecipePage);
            deleteButton = itemView.findViewById(R.id.notificationDelete);
            notificationBorder = itemView.findViewById(R.id.notificationBorder);
            //imageView.setImageResource(R.drawable.orange_juice_glass);
            status = (TextView) itemView.findViewById(R.id.status);
            time =  itemView.findViewById(R.id.timeView);
            notifications = new ArrayList<>();
            //item = new ArrayList<>();
        }
    }


}
