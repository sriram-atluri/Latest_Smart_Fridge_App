package com.example.myapplication.firstFragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NotificationsData.db";
    public static final String TABLE_NAME = "Notifications";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOTIFICATION_ITEM";
    public static final String COL_3 = "STATUS";
    public static final String COL_4 = "TIME";

    public NotificationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTIFICATION_ITEM TEXT, STATUS TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addData(String notification, String status, String time){
        System.out.println("Entering");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, notification);
        contentValues.put(COL_3, status);
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
        db.execSQL("DELETE FROM Notifications WHERE ID='"+get_ID+"'");
    }

    public ArrayList<NotificationItem> getItems() {
        ArrayList<NotificationItem> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationItem notificationItem = new NotificationItem();
                notificationItem.setName(cursor.getString(1));
                notificationItem.setStatus(cursor.getString(2));
                notificationItem.setTime(cursor.getString(3));
                arrayList.add(notificationItem);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public int GetId(String entry) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getId = myDB.rawQuery("select ID from " + TABLE_NAME + " where NOTIFICATION_ITEM = '"+ entry +"'",null);
        if (getId != null && getId.moveToFirst()) {
            return getId.getInt(0);
        } else {
            //return null;  // because you have to return something
        }
        System.out.println(getId);
        return getId.getColumnIndex("id");
    }

    // Function to add low inventory to the Android device itself
    public void add_low_inventory_notification(String category){
        System.err.println("Category is " + category + " from add_low_inventory_notification");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh:mm aa");
        String currentDateandTime = sdf.format(new Date());
        addData(category, " is running low", currentDateandTime);
   }

    public void add_food_expiring_notification(String item, String daysToExpiration){
        System.err.println("Food expiring notification is " + item + daysToExpiration + " from food expiry notification");
        //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh:mm aa");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDateandTime = sdf.format(new Date());
        String expiration = "Expiring ";
        if (daysToExpiration.equals("0")) {
            expiration += "Today";
        } else if (daysToExpiration.equals("1")) {
            expiration += "in " +daysToExpiration + " day";
        } else {
            expiration += "in " + daysToExpiration + " days";
        }

        addData(item, expiration, currentDateandTime);
    }

}