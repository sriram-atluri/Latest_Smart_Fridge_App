package com.example.myapplication.cameraClassesAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class Camera3Fragment extends Fragment {

    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageView freezerShot;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera3, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        backButton = getView().findViewById(R.id.cameraBackButton);
        backButton.setOnClickListener(v->{
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new Camera2Fragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();

            getActivity().getFragmentManager().popBackStack();
        });

        forwardButton = getView().findViewById(R.id.cameraForwardButton);
        forwardButton.setOnClickListener(v -> {
            // Begin the transaction
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new Camera4Fragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        freezerShot = getView().findViewById(R.id.freezerShot);
        freezerShot.setImageResource(R.drawable.fz);
        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Camera3Fragment.this)
                        .navigate(R.id.action_First5Fragment_to_Second5Fragment);
            }
        });*/
    }
}