package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FreezerDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FreezerData.db";
    public static final String TABLE_NAME = "FreezerList";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FREEZER_ITEM";
    public static final String COL_3 = "EXPIRY_DATE";

    public FreezerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FREEZER_ITEM TEXT, EXPIRY_DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addData(String item, String expiryDate){
        System.out.println("Entering");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, expiryDate);

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
        db.execSQL("DELETE FROM FreezerList WHERE ID='"+get_ID+"'");
    }

    public ArrayList<InventoryItem> getItems() {
        ArrayList<InventoryItem> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InventoryItem freezerItem = new InventoryItem();
                freezerItem.setName(cursor.getString(1));
                freezerItem.setExpiryDate(cursor.getString(2));
                arrayList.add(freezerItem);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public int GetId(String entry) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getId = myDB.rawQuery("select ID from " + TABLE_NAME + " where FREEZER_ITEM = '"+ entry +"'",null);
        if (getId != null && getId.moveToFirst()) {
            return getId.getInt(0);
        } else {
            //return null;  // because you have to return something
        }
        System.out.println(getId);
        return getId.getColumnIndex("id");
    }
}
