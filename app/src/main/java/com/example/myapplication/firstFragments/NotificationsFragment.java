package com.example.myapplication.firstFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.InventoryScreen;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.activityClasses.AboutFragment;
import com.example.myapplication.activityClasses.AboutPage;
import com.example.myapplication.activityClasses.CameraActivity;
import com.example.myapplication.activityClasses.CartActivity;
import com.example.myapplication.activityClasses.ContactUsFragment;
import com.example.myapplication.activityClasses.ContactsPage;
import com.example.myapplication.activityClasses.HelpFragment;
import com.example.myapplication.activityClasses.HelpPage;
import com.example.myapplication.cameraClassesAndFragments.Camera1Fragment;
import com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses.InventoryScreenFragment;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileScreenFragment;
import com.example.myapplication.profileSettingsClassesAndFragments.ProfileSettingsActivity;
import com.example.myapplication.recipeClassesAndFragments.RecipeScreen;
import com.example.myapplication.recipeClassesAndFragments.RecipeFragment;
import com.example.myapplication.cartFiles.ShoppingCartFragment;

public class NotificationsFragment extends Fragment {

    private ImageButton backButton;
    Intent i;
    private static NotificationsFragment instance;
    private MQTTHandler mqttHandler;
    String fridgeTemperatureValue;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_notifications_revised, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NotificationsFragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
            }
        });*/

        NotificationListFragment notificationListFragment = new NotificationListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.notifications_placeholder, notificationListFragment).commit();

        backButton = getView().findViewById(R.id.backButton);

        backButton.setOnClickListener(v->{
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.your_placeholder, new HomeFragment()).commit();

            if (getActivity() instanceof MainActivity2){
                try {
                    mqttHandler.enable_temp();
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fridgeTemperatureValue = mqttHandler.get_fridge_temp();
                mqttHandler.disable_temp();

                Bundle bundle = new Bundle();                                                           // Creates a bundle object o pass data values to a fragment or activity
                bundle.putString("fridgeTemp", fridgeTemperatureValue);                                 // The fridge temperature value is mapped to the fridgeTemp key */
                HomeFragment hm = new HomeFragment();                                                   // Creates a HomeFragment object
                hm.setArguments(bundle);                                                                // Sends the arguments from the bundle
                //getParentFragmentManager().beginTransaction()
               //         .replace(R.id.your_placeholder, hm)
               //         .commit();

                getParentFragmentManager().beginTransaction().replace(R.id.your_placeholder, hm).commit();
            }
            else if (getActivity() instanceof RecipeScreen) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.recipe_placeholder, new RecipeFragment()).commit();
            }
            else if (getActivity() instanceof InventoryScreen){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inventoryPlaceholder, new InventoryScreenFragment()).commit();
            }
            else if (getActivity() instanceof CameraActivity){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cameraPlaceholder, new Camera1Fragment()).commit();
            }
            else if (getActivity() instanceof CartActivity){
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.notifications_placeholder, new CartActivity()).commit());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cartContainer, new ShoppingCartFragment()).commit();
            }
            else if (getActivity() instanceof AboutPage){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.aboutActivityContainer, new AboutFragment()).commit();
            }
            else if (getActivity() instanceof ContactsPage){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contactUsActivityContainer, new ContactUsFragment()).commit();
            }
            else if (getActivity() instanceof HelpPage){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.helpPageContainer, new HelpFragment()).commit();
            }
            else if (getActivity() instanceof ProfileSettingsActivity) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileActivityContainer, new ProfileScreenFragment()).commit();
            }

        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}