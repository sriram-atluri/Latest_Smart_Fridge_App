package com.example.myapplication.inventoryFragmentNestedFragmentsAndRelatedClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryItem {

    private String name;
    private String expiryDate;

    public InventoryItem(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");    // Creates the date formatter
        String currentDateandTime = sdf.format(new Date()); // Gets the current time
        long timespan = sdf.parse(expiryDate).getTime() - sdf.parse(currentDateandTime).getTime();
        int dayCount = (int) ((timespan)/(1000*60*60*24));
        expiryDate = String.valueOf(dayCount);
        switch (expiryDate) {
            case "0":
                expiryDate = "Expiring\n   today";
                break;
            case "1":
                expiryDate = "Expiring in\n  " + dayCount + " day";
                break;
            default:
                expiryDate = "Expiring in\n  " + dayCount + " days";
                break;
        }
        return expiryDate;
    }

}