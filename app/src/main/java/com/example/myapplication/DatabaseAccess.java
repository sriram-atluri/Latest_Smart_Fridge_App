package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.UpdateItemOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.List;
import java.util.UUID;

// Database Access consisting of authorization credentials and the necessary CRUD (Create, Read, Update, and Delete) methods
public class DatabaseAccess extends AppCompatActivity {


    private String TAG = "DynamoDB";

    // The Cognito credentials are necessary in order to access the DynamoDB
    private final String COGNITO_IDENTITY_POOL_ID = "us-west-2:ec3c1f28-16f4-4cc0-a0d9-5112787d457e";
    private final Regions COGNITO_IDENTITY_POOL_REGION = Regions.US_WEST_2;

    private final String tableName = "Table";                                       // The name of our table in the DynamoDB
    private Context context;
    private CognitoCachingCredentialsProvider credentialsProvider;                      // Launches the login for authorization
    private AmazonDynamoDBClient dbClient;                                              // Creates a DynamoDBClient instance
    private Table dTable;                                                               // Creates a Table variable to perform our necessary query or create commands

    private static volatile DatabaseAccess instance;                                    // Creates an instance of DatabaseAccess when initiated from MainActivity

    // DatabaseAccess constructor
    public DatabaseAccess(Context context){

        System.out.println("Entering constructor");                                                         // Debug statement 7
        this.context = context;
        System.out.println("Fetching context");                                                             // Debug statement 8
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                COGNITO_IDENTITY_POOL_ID, // Identity pool ID
                COGNITO_IDENTITY_POOL_REGION // Region
        );
        System.out.println("Passed in Cognito credentials");                                                // Debug statement 9
        // Create a connection to DynamoDB
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        System.out.println("Initialized DB client");                                                        // Debug statement 10
        // Sets the region for the dbClient
        dbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        System.out.println("Configured DB Client Region!");                                                 // Debug statement 11
        // Create a table reference
        dTable = Table.loadTable(dbClient, tableName);                          // Table loading error has been resolved with the Strict Mode reconfigurations
        System.out.println("Loaded the table!");                                                            // Debug statement 12
    }


    public void create(){
        Document food = new Document();
        food.put("Food Item", "Chicken");
        food.put("Food Category", "Non-Vegetarian");
        food.put("Food Item", "Shrimp");
        food.put("Food Category", "Non-Vegetarian");
        food.put("Food Item", "Water");
        food.put("Food Category", "Beverages");
        food.put("Food Item", "Granola Bar");
        food.put("Food Category", "Snack");

        food.put("Food Item", "Protein Bar");
        food.put("Food Category", "Snack");


        /*
        // If the following keys don't exist, then they are created
        if (!memo.containsKey("userId")) {
            memo.put("userId", credentialsProvider.getCachedIdentityId());
        }
        if (!memo.containsKey("noteId")) {
            memo.put("noteId", UUID.randomUUID().toString());
        }
        if (!memo.containsKey("creationDate")) {
            memo.put("creationDate", System.currentTimeMillis());
        }*/

        // The memo object is then placed as an item in the DynamoDB database table
        dTable.putItem(food);
    }

    public void update(Document memo) {
        Document document = dTable.updateItem(memo, new UpdateItemOperationConfig().withReturnValues(ReturnValue.ALL_NEW));
    }

    public void delete() {
        /*
        dTable.deleteItem(
                memo.get("userId").asPrimitive(),
                memo.get("noteId").asPrimitive());
                */
        Document food = new Document();
        dTable.deleteItem(food.get("Shrimp").asPrimitive());
    }

    public Document getMemoById(String id) {
        return dTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(id));
    }
    public List<Document> getAll() {
        return dTable.query(new Primitive(credentialsProvider.getCachedIdentityId())).getAllResults();
    }

    /*
    private class CreateItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(DatabaseAccess.this);
            databaseAccess.create(documents[0]);
            return null;
        }
    }*/


    public static synchronized DatabaseAccess getInstance(Context context) {
        System.out.println("Debug spot");
        if (instance == null){ // If instance is null
            System.out.println("Entering into the instance's if condition");
            instance = new DatabaseAccess(context);
        }
        //return null;
        return instance;                                                                        // Returns the instance
    }



}