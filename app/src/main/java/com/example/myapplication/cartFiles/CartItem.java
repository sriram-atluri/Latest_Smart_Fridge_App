package com.example.myapplication.cartFiles;


import com.example.myapplication.R;

// This class configures and retrieves the respective attributes for each Cart Item object
public class CartItem {

    private String id;
    private String foodName;
    private String quantity;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    //public String toString(){
      //  return "Food Item: " + " Item Name-" + foodName;
   // }
}
