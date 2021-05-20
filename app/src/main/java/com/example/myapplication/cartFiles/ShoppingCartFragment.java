package com.example.myapplication.cartFiles;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.firstFragments.ListingStyle;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private RecyclerView recycler_cart;
    private ImageButton addItem;
    CartAdapter cartList;
    List<CartItem> cart = new ArrayList<>();

    Intent i;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getIntent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShoppingCartDBHelper shoppingCartDBHelper = new ShoppingCartDBHelper(getActivity());
        recycler_cart = (RecyclerView) getView().findViewById(R.id.recycler_cart);
        addItem = getView().findViewById(R.id.addShoppingCartItem);


        addItem.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog_Alert);
            final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.shopping_cart_request_form, null);
            builder.setView(customLayout);
            EditText foodItem = customLayout.findViewById(R.id.shoppingCartChoice);
            builder.setPositiveButton("Cancel", (dialog, which) -> {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Confirm", (dialog, which) ->
            {
                shoppingCartDBHelper.addFoodItem(foodItem.getText().toString(), "1");
                Toast.makeText(getContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                displayNotifications();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        });
        displayNotifications();

    }

    public void displayNotifications(){
        ShoppingCartDBHelper dbHelper = new ShoppingCartDBHelper(getActivity());
        System.out.println("Entered display notifications");
        cart = new ArrayList<>(dbHelper.getItems());
        recycler_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_cart.setItemAnimator(new DefaultItemAnimator());
        cartList = new CartAdapter(getActivity(), cart);
        recycler_cart.setAdapter(cartList);
    }

}