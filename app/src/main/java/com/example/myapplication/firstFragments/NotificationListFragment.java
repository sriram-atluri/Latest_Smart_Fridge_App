package com.example.myapplication.firstFragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;
import com.example.myapplication.recipeClassesAndFragments.IngredientAdapter;
import com.example.myapplication.recipeClassesAndFragments.IngredientItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class NotificationListFragment extends Fragment {

    private RecyclerView recycledNotifications;
    private NotificationAdapter notificationAdapter;
    private ImageButton notificationDelete;
    List<NotificationItem> notificationItemList;
    private Button addButton;
    MQTTHandler mqttHandler;
    //private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mqttHandler = new MQTTHandler(getContext());
        return inflater.inflate(R.layout.fragment_notifications_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycledNotifications = getActivity().findViewById(R.id.notificationsRecycle);

        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //notificationItemList.clear();

        /*
        addButton = getView().findViewById(R.id.AddButton);
        addButton.setOnClickListener(v->{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh:mm aa");
            String currentDateandTime = sdf.format(new Date());
            notificationDBHelper.addData("Test", "Running low", currentDateandTime);
            //System.out.println(currentDateandTime);
            System.out.println("Entered button");
            //notificationAdapter.notifyDataSetChanged();
            displayNotifications();
        });*/
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        displayNotifications();
        //notificationAdapter = new NotificationAdapter(getActivity(), notificationItemList);
        //recycledNotifications.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //recycledNotifications.setAdapter(notificationAdapter);

        /*
        notificationDelete.setOnClickListener(v->{
            try {
                notificationItemList.remove(notificationItemList.get(0));
                notificationAdapter.notifyDataSetChanged();
            } catch (IndexOutOfBoundsException e) {
                //Toast.makeText(getActivity().getApplicationContext(), "All notifications removed", Toast.LENGTH_LONG);
                System.out.println("All notifications removed");
            }
        });*/
    }

    public void displayNotifications(){
        NotificationDBHelper dbHelper = new NotificationDBHelper(getActivity());
        notificationItemList = new ArrayList<>(dbHelper.getItems());
        //notificationItemList = new ArrayList<>();
        /*notificationItemList.add(new NotificationItem("Cheese", "Expiring in 1 day(s)", "05/16/2021"));
        notificationItemList.add(new NotificationItem("Milk", "Expiring in 1 day(s)", "05/16/2021"));
        notificationItemList.add(new NotificationItem("Water", "Expiring in 2 days", "05/16/2021"));
        notificationItemList.add(new NotificationItem("Egg", "Expiring in 2 days", "05/16/2021"));
        notificationItemList.add(new NotificationItem("Salmon", "Expiring Today", "05/16/2021"));*/
       // System.out.println(dbHelper.getItems());
        Collections.sort(notificationItemList, NotificationItem.DESCENDING_ORDER);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycledNotifications.setLayoutManager(linearLayoutManager);
        recycledNotifications.setItemAnimator(new DefaultItemAnimator());
        ListingStyle listingStyle = new ListingStyle(55);
        recycledNotifications.addItemDecoration(listingStyle);
        notificationAdapter = new NotificationAdapter(getActivity(), notificationItemList);
        recycledNotifications.setAdapter(notificationAdapter);
    }
}
