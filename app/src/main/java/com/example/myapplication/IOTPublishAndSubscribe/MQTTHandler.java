package com.example.myapplication.IOTPublishAndSubscribe;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttMessageDeliveryCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;
import com.example.myapplication.firstFragments.NotificationDBHelper;
import com.example.myapplication.firstFragments.NotificationsFragment;


import software.amazon.freertos.amazonfreertossdk.AmazonFreeRTOSConstants.MqttConnectionState;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MQTTHandler {
    private Context context;
    private static MQTTHandler mqtt; // Using same instance for all instantiation

    // AWS Setup
    private AWSIotMqttManager mIotMqttManager;
    private MqttConnectionState mMqttConnectionState = MqttConnectionState.MQTT_Disconnected;

    // Pool and client credentials
    private String identityPoolId = "us-west-2:ec3c1f28-16f4-4cc0-a0d9-5112787d457e";
    private CognitoCachingCredentialsProvider credentialsProvider;


    // Constant variables that shouldn't be changed
    private static final String endpoint = "a3fqhlf39hbti2-ats.iot.us-west-2.amazonaws.com";
    private static final String clientID = "Smart_Fridge_App";
    private static final String TAG = "MQTT Handler Class";

    // Topics incoming from the app
    private static final String in_inventory_notification_topic = "rpi/inventory/notification/tx";
    private static final String in_recipe_topic = "rpi/recipe/tx";
    private static final String in_temperature_topic = "hardware/temperature/tx";

    // Topics outgoing from the app
    private static final String out_inventory_delete_topic = "rpi/inventory/delete/rx";
    private static final String out_inventory_add_topic = "rpi/inventory/add/rx";
    private static final String out_temperature_topic = "hardware/temperature/rx";
    private static final String out_camera_topic = "camera/request/rx";
    private static final String out_recipe_topic = "rpi/recipe/rx";

    // These are for temperature
    private String fridge_temp;

    // These are for recipe data. These need to be read together
    private int num_of_recipes;
    List<String> titles;
    List<String> urls;
    List<String> times;

    public static MQTTHandler getInstance(Context context) throws InterruptedException {
        if (mqtt == null) {
            mqtt = new MQTTHandler(context);
            mqtt.aws_iot_connect();
            while (!mqtt.isMqttConnected()) {
                Thread.sleep(10);
            }
        }
        return mqtt;
    }

    // Private Constructor
    private MQTTHandler (Context context) {
        this.context = context;
        this.fridge_temp = "N/A";
        this.num_of_recipes = 0;
        this.titles = new ArrayList<String>();
        this.urls = new ArrayList<String>();
        this.times = new ArrayList<String>();
        this.mMqttConnectionState = MqttConnectionState.MQTT_Disconnected;
        this.credentialsProvider = new CognitoCachingCredentialsProvider(this.context, this.identityPoolId, Regions.US_WEST_2);
    }
    /*
        Getter Functions
     */
    public String get_fridge_temp() { return this.fridge_temp; }
    public int get_num_of_recipes() { return this.num_of_recipes; }
    public String[] get_recipe_titles() { return this.titles.toArray(new String[0]); }
    public String[] get_recipe_urls() { return this.urls.toArray(new String[0]); }
    public String[] get_recipe_times() { return this.times.toArray(new String[0]); }
    public boolean isMqttConnected() { return (this.mMqttConnectionState == MqttConnectionState.MQTT_Connected); }


    /*
        Functions to enable/disable temperature
     */
    /**
     * Request Arduino to enable temperature.
     */
    public void enable_temp() {
        String payload = "{\"request\":\"enable\"}";
        mqtt_publish(out_temperature_topic, payload);
    }

    /**
     * Request Arduino to disable temperature.
     */
    public void disable_temp() {
        String payload = "{\"request\":\"disable\"}";
        mqtt_publish(out_temperature_topic, payload);
    }

    /*
        Function to request pictures of fridge
     */
    /**
     * Request ESP32-CAM to take picture of fridge.
     */
    public void take_picture() {
        String payload = "{\"request\":\"click\"}";
        mqtt_publish(out_camera_topic, payload);
    }

    /*
        Function to request recipe
     */
    /**
     * Send a recipe request message to Raspberry Pi.
     *
     * @param items   list of food items to request recipe for.
     */
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
        mqtt_publish(out_recipe_topic, payload);
    }

    public void request_recipe(String items) {
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();
        String payload;

        obj.put("num of items", 1);
        obj.put("items", Arrays.asList(items));

        try {
            obj.writeJSONString(out);
        } catch (Exception e) {
            System.out.println("JSON write string did not work");
            System.out.println("Cannot publish to rpi/recipes/rx");
            return;
        }
        payload = out.toString();
        mqtt_publish(out_recipe_topic, payload);
    }

    /**
     * Send a request to remove item from Raspberry Pi inventory.
     *
     * @param item   Item to remove from inventory.
     */
    public void remove_from_inventory(String item) {
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();
        String payload;

        obj.put("item", item);
        try {
            obj.writeJSONString(out);
        } catch (Exception e) {
            System.out.println("JSON write string did not work");
            System.out.println("Cannot publish to rpi/inventory/delete/rx");
            return;
        }
        payload = out.toString();
        mqtt_publish(out_inventory_delete_topic, payload);
    }

    /**
     * Request to add to Raspberry Pi Inventory
     *
     * @param item              Item to add.
     * @param category          fridge/freezer/pantry.
     * @param expiration_date   date of expiration.
     */
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
        mqtt_publish(out_inventory_add_topic, payload);
    }

    /*
        Handler Code for incoming topics
     */
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
            new NotificationDBHelper(context).add_low_inventory_notification(category); // Sriram to add !!!!!!!!!!!!!!!!!!
            System.out.println(category + " is running low");
        } else if (notification.equals("food expiring")) {
            num = payload.get("num of items").hashCode();
            if (num == 0) return;

            items = string_cleaner_to_array(payload.get("items").toString());
            days_to_expiration = string_cleaner_to_array(payload.get("days to expiration").toString());
            for (int i = 0; i < num; i++) {
                System.out.printf("%s is expiring in %s day(s)\n", items[i], days_to_expiration[i]);
                new NotificationDBHelper(context).add_food_expiring_notification(items[i], days_to_expiration[i]); // Sriram to add!!!!!!!!!!!!!!
            }
        } else {
            System.out.printf("Invalid notification: %s\n", notification);
        }
    }

    public void rpi_handler(final String topic, String data) {
        System.out.printf("Got topic %s\n", topic);
        JSONObject payload = parse_payload(data);
        switch (topic) {
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

    private void hardware_temperature_handler(final String topic, String data) {
        System.out.printf("Got topic: %s\n", topic);
        JSONObject payload = parse_payload(data);
        this.fridge_temp = payload.get("fridge").toString()+"\u00B0F";
        System.out.printf("Fridge Temp: %s \n", this.fridge_temp);
    }

    /*
        Helper Functons to cleanup data and parse message
     */
    // Functions to handle cleanup and parsing
    private String[] string_cleaner_to_array(String s) {
        return s.replace("[","")
                .replace("]","")
                .replace("\"", "")
                .replace("\\", "")
                .split(",");
    }

    private JSONObject parse_payload(String message) {
        JSONParser parser = new JSONParser();
        JSONObject payload;
        try {
            Object obj = parser.parse(message);
            payload = (JSONObject)obj;
        } catch (ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            return null;
        }
        return payload;
    }


    /*
        Helper Function to publish to topics
     */
    private void mqtt_publish(final String topic, final String payload) {
        if (mMqttConnectionState != MqttConnectionState.MQTT_Connected) {
            Log.e(TAG, "Cannot publish message to IoT because MQTT connection state" +
                    " is not connected.");
            return;
        }
        AWSIotMqttMessageDeliveryCallback deliveryCallback = new AWSIotMqttMessageDeliveryCallback() {
            @Override
            public void statusChanged(MessageDeliveryStatus status, Object userData) {
                Log.d(TAG, "Publish msg delivery status: " + status.toString());
            }
        };
        try {
            Log.i(TAG, "Sending MQTT message to IoT on topic: " + topic
                    + " message: " + new String(payload));
            mIotMqttManager.publishString(payload, topic, AWSIotMqttQos.QOS0,
                    deliveryCallback, null);
        } catch (Exception e) {
            Log.e(TAG, "Publish error.", e);
        }
    }

    private void setMqttConnectionState(MqttConnectionState state) {
        this.mMqttConnectionState = state;
    }

    public void aws_iot_connect() {
        if (mMqttConnectionState == MqttConnectionState.MQTT_Connected) {
            Log.w(TAG, "Already connected to IOT.");
            return;
        }
        if (mMqttConnectionState != MqttConnectionState.MQTT_Disconnected) {
            Log.w(TAG, "Previous connection is active, please retry or disconnect MQTT first.");
            return;
        }
        mIotMqttManager = new AWSIotMqttManager(clientID, endpoint);

        AWSIotMqttClientStatusCallback mqttClientStatusCallback = new AWSIotMqttClientStatusCallback() {
            @Override
            public void onStatusChanged(AWSIotMqttClientStatus status, Throwable throwable) {
                Log.i(TAG, "MQTT connection status changed to: " + String.valueOf(status));
                switch (status) {
                    case Connected:
                        mqtt.setMqttConnectionState(MqttConnectionState.MQTT_Connected);
                        mqtt.mqtt_subcribe();
                        break;
                    case Connecting:
                    case Reconnecting:
                        mqtt.setMqttConnectionState(MqttConnectionState.MQTT_Connecting);
                        break;
                    case ConnectionLost:
                        mqtt.setMqttConnectionState(MqttConnectionState.MQTT_Disconnected);
                        break;
                    default:
                        Log.e(TAG, "Unknown MQTT connection state: " + status);
                        return;
                }
            }
        };
        mIotMqttManager.connect(credentialsProvider, mqttClientStatusCallback);
    }

    private void mqtt_subcribe() {
        if (mMqttConnectionState != MqttConnectionState.MQTT_Connected) {
            Log.e(TAG, "Cannot subscribe because MQTT state is not connected.");
            return;
        }

        try {
            Log.i(TAG, "Subscribing to IoT on topic : " + in_inventory_notification_topic);
            mIotMqttManager.subscribeToTopic(in_inventory_notification_topic, AWSIotMqttQos.QOS1,
                    new AWSIotMqttNewMessageCallback() {
                        @Override
                        public void onMessageArrived(final String topic, final byte[] data) {
                            try {
                                String message =  new String(data, "UTF-8");
                                mqtt.rpi_handler(topic, message);
                            } catch (UnsupportedEncodingException e) {
                                Log.e(TAG, "Message encoding error.", e);
                            }
                        }
                    });
            Log.i(TAG, "Subscribed to : " + in_inventory_notification_topic);

            Log.i(TAG, "Subscribing to IoT on topic : " + in_recipe_topic);
            mIotMqttManager.subscribeToTopic(in_recipe_topic, AWSIotMqttQos.QOS1,
                    new AWSIotMqttNewMessageCallback() {
                        @Override
                        public void onMessageArrived(final String topic, final byte[] data) {
                            try {
                                String message =  new String(data, "UTF-8");
                                mqtt.rpi_handler(topic, message);
                            } catch (UnsupportedEncodingException e) {
                                Log.e(TAG, "Message encoding error.", e);
                            }
                        }
                    });
            Log.i(TAG, "Subscribed to : " + in_recipe_topic);

            Log.i(TAG, "Subscribing to IoT on topic : " + in_temperature_topic);
            mIotMqttManager.subscribeToTopic(in_temperature_topic, AWSIotMqttQos.QOS1,
                    new AWSIotMqttNewMessageCallback() {
                        @Override
                        public void onMessageArrived(final String topic, final byte[] data) {
                            try {
                                String message =  new String(data, "UTF-8");
                                mqtt.hardware_temperature_handler(topic, message);
                            } catch (UnsupportedEncodingException e) {
                                Log.e(TAG, "Message encoding error.", e);
                            }
                        }
                    });
            Log.i(TAG, "Subscribed to : " + in_temperature_topic);
        } catch (Exception e) {
            Log.e(TAG, "Subscription failed", e);
        }
    }
}
