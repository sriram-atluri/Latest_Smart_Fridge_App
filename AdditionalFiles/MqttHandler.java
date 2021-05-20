package com.example.mqttwork;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MqttHandler{
    private Context context;
    private AWSIotMqttClient client;

    // These are for temperature
    private String fridge_temp;

    // These are for recipe data. These need to be read together
    private int num_of_recipes;
    List<String> titles;
    List<String> urls;
    List<String> times;

    public MqttHandler(Context context) {
        this.context = context;
        this.fridge_temp = "";
        this.num_of_recipes = 0;
        this.titles = new ArrayList<String>();
        this.urls = new ArrayList<String>();
        this.times = new ArrayList<String>();
        //this.client = client;
    }

    public String get_fridge_temp() {
        return this.fridge_temp;
    }

    public int get_num_of_recipes() { return this.num_of_recipes; }

    public String[] get_recipe_titles() { return this.titles.toArray(new String[0]); }

    public String[] get_recipe_urls() { return this.urls.toArray(new String[0]); }

    public String[] get_recipe_times() {
        return this.times.toArray(new String[0]);
    }

    // Must call this function to start the temperature module
    public void enable_temp() {
        String payload = "{\"request\":\"enable\"}";
        try {
            this.client.publish("hardware/temperature/rx", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Call this function to disable temperature module
    public void disable_temp() {
        String payload = "{\"request\":\"disable\"}";
        try {
            this.client.publish("hardware/temperature/rx", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void request_recipe(String[] items) {
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();
        String payload;

        obj.put("num of items",items.length);
        obj.put("items", Arrays.asList(items));

        try {
            obj.writeJSONString(out);
        } catch (Exception e) {
            System.out.println("JSON write string did not work");
            System.out.println("Cannot publish to rpi/recipes/rx");
            return;
        }
        payload = out.toString();
        System.out.print(payload);
        try {
            this.client.publish("rpi/recipe/rx", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Expiration date should be in yyyy-mm-dd format
    public void add_inventory(String item, String category, String expiration_date) {
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();
        String payload;

        obj.put("item", item);
        obj.put("category", category);
        obj.put("expiration", expiration_date);
        try {
            obj.writeJSONString(out);
        } catch (Exception e) {
            System.out.println("JSON write string did not work");
            System.out.println("Cannot publish to rpi/inventory/add/rx");
            return;
        }
        payload = out.toString();
        System.out.print(payload);
        try {
            this.client.publish("rpi/inventory/add/rx", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] string_cleaner_to_array(String s) {
        return s.replace("[","")
                .replace("]","")
                .replace("\"", "")
                .split(",");
    }

    private JSONObject parse_payload(AWSIotMessage message) {
        JSONParser parser = new JSONParser();
        JSONObject payload;
        try {
            Object obj = parser.parse(message.getStringPayload());
            payload = (JSONObject)obj;
        } catch (ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            return null;
        }
        return payload;
    }

    private void rpi_recipe_handler(JSONObject payload) {
        System.out.println("Processing recipe...");
        this.num_of_recipes = payload.get("num_of_recipes").hashCode();
        this.titles = Arrays.asList(string_cleaner_to_array(payload.get("titles").toString()));
        this.urls = Arrays.asList(string_cleaner_to_array(payload.get("urls").toString()));
        this.times = Arrays.asList(string_cleaner_to_array(payload.get("times").toString()));

        System.out.printf("Found %d recipes\n", this.num_of_recipes);

        for (int i = 0; i < this.num_of_recipes; i++) {
            System.out.printf("Title: %s\n", this.titles.get(i));
            System.out.printf("URL: %s\n", this.urls.get(i));
            System.out.printf("Time: %s\n", this.times.get(i));
        }
    }

    private void rpi_notification_handler(JSONObject payload) {
        System.out.println("Got inventory notification");
        String notification = payload.get("notification").toString().toLowerCase();
        String category;
        int num;
        String[] items;
        String[] days_to_expiration;
        System.out.println("Notification is " + notification);
        if (notification.equals("low inventory")) {
            category = payload.get("category").toString();
            //add_low_inventory_notification(category); // Sriram to add !!!!!!!!!!!!!!!!!!
            System.out.println(category + " is running low");
        } else if (notification.equals("food expiring")) {
            num = payload.get("num of items").hashCode();
            if (num == 0) return;

            items = string_cleaner_to_array(payload.get("items").toString());
            days_to_expiration = string_cleaner_to_array(payload.get("days to expiration").toString());
            for (int i = 0; i < num; i++) {
                System.out.printf("%s is expiring in %s day(s)\n", items[i], days_to_expiration[i]);
                //add_food_expiring_notification(items[i], days_to_expiration[i]); // Sriram to add!!!!!!!!!!!!!!
            }
        } else {
            System.out.printf("Invalid notification: %s\n", notification);
        }
    }

    public void rpi_handler(AWSIotMessage message) {
        System.out.printf("Got topic %s\n", message.getTopic());
        JSONObject payload = parse_payload(message);
        switch (message.getTopic()) {
            case "rpi/inventory/notification/tx":
                rpi_notification_handler(payload);
                break;
            case "rpi/recipe/tx":
                rpi_recipe_handler(payload);
                break;
            default:
                System.out.println("Invalid topic for rpi");
                break;
        }
    }

    public void hardware_temperature_handler(AWSIotMessage message) {
        System.out.printf("Got topic: %s\n", message.getTopic());
        JSONObject payload = parse_payload(message);
        this.fridge_temp = payload.get("fridge").toString()+"\u00B0F";
        System.out.printf("Fridge Temp: %s \n", this.fridge_temp);
    }

    // Daniel's changes
    public void aws_subscribe() {
        //Creates an AWS Internet of Things Message Queueing Telemetry Transport client to initiate connection with Amazon's IOT service
        AWSIotMqttClient c= new AWSIotMqttClient("a3fqhlf39hbti2-ats.iot.us-west-2.amazonaws.com",
                "arn:aws:iot:us-west-2:656922978498:thing/AndroidSmartFridgeApp/client/3405dqrmjes3j39lo0pgopnacc",
                "AKIAZR45JFDBI6WHY6AC",
                "59beme0r6dqNm36UEtR6rsTm4HvAVWFVRX/+J0XV");

        this.client = c;

        try {
            this.client.connect();                               // Connects to the client
            System.out.println("connection executed");      // If connection succeeds
        } catch (AWSIotException e) {
            e.printStackTrace();
        }

        // This subscribes to all rpi/inventory/notification/tx topics
        AWSIotTopic rpi_topic = new AWSIotTopic("rpi/inventory/notification/tx", AWSIotQos.QOS1) {
            @Override
            public void onMessage(AWSIotMessage message) {
                rpi_handler(message);
            }};
        try {
            this.client.subscribe(rpi_topic, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // This subscribes to rpi/recipe/tx
        AWSIotTopic rpi_recipe_topic = new AWSIotTopic("rpi/recipe/tx", AWSIotQos.QOS1) {
            @Override
            public void onMessage(AWSIotMessage message) {
                rpi_handler(message);
            }};
        try {
            this.client.subscribe(rpi_recipe_topic, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // This subscribes to hardware/temperature/tx
        AWSIotTopic hardware_topic = new AWSIotTopic("hardware/temperature/tx", AWSIotQos.QOS1) {
            @Override
            public void onMessage(AWSIotMessage message) {
                hardware_temperature_handler(message);
            }};
        try {
            this.client.subscribe(hardware_topic, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
