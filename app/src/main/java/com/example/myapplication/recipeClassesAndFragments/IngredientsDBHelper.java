package com.example.myapplication.recipeClassesAndFragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.firstFragments.NotificationItem;

import java.util.ArrayList;

public class IngredientsDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "IngredientDatabase.db";
    public static final String TABLE_NAME = "Ingredients";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Ingredient";

    public IngredientsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Ingredient TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addItem(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public void deleteItem(int get_ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Ingredients WHERE ID='"+get_ID+"'");
        db.close();
    }

    public ArrayList<IngredientItem> getItems() {
        ArrayList<IngredientItem> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                IngredientItem ingredientItem = new IngredientItem();
                ingredientItem.setIngredientName(cursor.getString(1));
                //notificationItem.setName(cursor.getString(1));
                arrayList.add(ingredientItem);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public int GetId(String entry) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getId = myDB.rawQuery("select ID from " + TABLE_NAME + " where Ingredient = '"+ entry +"'",null);
        if (getId != null && getId.moveToFirst()) {
            return getId.getInt(0);
        } else {
            //return null;  // because you have to return something
        }
        System.out.println(getId);
        return getId.getColumnIndex("id");
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete *FROM"+ TABLE_NAME);
        //db.execSQL("TRUNCATE table" + TABLE_NAME);
        db.close();
    }
}

