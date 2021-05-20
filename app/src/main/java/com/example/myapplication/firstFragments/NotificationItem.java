package com.example.myapplication.firstFragments;

import java.util.Comparator;

public class NotificationItem {

    private String ID;
    private String name;
    private String status;
    private String time;

    public NotificationItem(){

    }

    public NotificationItem(String name, String status, String time){
        this.name = name;
        this.status = status;
        this.time = time;
    }

    public static final Comparator<NotificationItem> DESCENDING_ORDER = new Comparator<NotificationItem>() {
        @Override
        public int compare(NotificationItem o1, NotificationItem o2) {
            return o1.getStatus().compareTo(o2.getStatus());
        }
    };

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
