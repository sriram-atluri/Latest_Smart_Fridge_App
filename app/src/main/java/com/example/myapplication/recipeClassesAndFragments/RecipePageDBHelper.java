package com.example.myapplication.recipeClassesAndFragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RecipePageDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "RecipeDataLists.db";
    public static final String TABLE_NAME = "Recipe_List";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Recipe_Item";
    public static final String COL_3 = "Recipe_URL";
    public static final String COL_4 = "Recipe_Time";

    public RecipePageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Recipe_Item TEXT, Recipe_URL TEXT, Recipe_Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addItem(String item, String url, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, url);
        contentValues.put(COL_4, time);

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
        db.execSQL("DELETE FROM Recipe_List WHERE ID='"+get_ID+"'");
    }


    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public int GetId(String entry) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getId = myDB.rawQuery("select ID from " + TABLE_NAME + " where Recipe_Item = '"+ entry +"'",null);
        if (getId != null && getId.moveToFirst()) {
            return getId.getInt(0);
        } else {
            //return null;  // because you have to return something
        }
        System.out.println(getId);
        return getId.getColumnIndex("id");
    }

    public ArrayList<RecipeItem> getItems() {
        ArrayList<RecipeItem> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecipeItem recipeItem = new RecipeItem();
                recipeItem.setFoodName(cursor.getString(1));
                recipeItem.setUrl(cursor.getString(2));
                recipeItem.setTime(cursor.getString(3));
                arrayList.add(recipeItem);
            }while (cursor.moveToNext());
        }
        return arrayList;
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
