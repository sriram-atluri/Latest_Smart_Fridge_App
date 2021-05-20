package com.example.myapplication.cartFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ShoppingCartDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ShoppingData2.db";
    public static final String TABLE_NAME = "Shopping_Cart";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SHOPPING_CART_ITEM";
    public static final String COL_3 = "QUANTITY";

    public ShoppingCartDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SHOPPING_CART_ITEM TEXT, QUANTITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addFoodItem(String item, String quantity){
        System.out.println("Entering");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, quantity);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public void updateItem(String ID, String item, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SHOPPING_CART_ITEM", item);
        contentValues.put("QUANTITY", quantity);
        //updating row
        db.update(TABLE_NAME, contentValues, "ID=" + ID, null);
        db.close();
    }
    public void deleteItem(int get_ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Shopping_Cart WHERE ID='"+get_ID+"'");
    }

    public ArrayList<CartItem> getItems() {
        ArrayList<CartItem> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartItem cartItem = new CartItem();
                cartItem.setId(cursor.getString(0));
                cartItem.setFoodName(cursor.getString(1));
                cartItem.setQuantity(cursor.getString(2));
                arrayList.add(cartItem);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public int GetId(String entry) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getId = myDB.rawQuery("select ID from " + TABLE_NAME + " where SHOPPING_CART_ITEM = '"+ entry +"'",null);
        if (getId != null && getId.moveToFirst()) {
            return getId.getInt(0);
        } else {
            //return null;  // because you have to return something
        }
        System.out.println(getId);
        return getId.getColumnIndex("id");
    }

}
