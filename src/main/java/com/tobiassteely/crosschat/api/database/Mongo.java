package com.tobiassteely.crosschat.api.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.config.Config;
import org.bson.Document;

public class Mongo {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> responses;
    private MongoCollection<Document> requests;

    public Mongo() {
        //Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        //mongoLogger.setLevel(Level.WARNING);
        Config config = CrossChat.getInstance().getConfigManager().getConfig("settings.json");

        String mongoUser = config.getString("mongodb-username");
        Log.sendMessage(0, "mongo user " + mongoUser);
        String mongoPass = config.getString("mongodb-password");
        String mongoHost = config.getString("mongodb-host");
        String mongoDatabase = config.getString("mongodb-db");
        String mongoAuthDatabase = config.getString("mongodb-authdb");

        ConnectionString connString = new ConnectionString(
                "mongodb://" + mongoUser + ":" + mongoPass + "@" + mongoHost + "/" + mongoAuthDatabase + "?w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        client = MongoClients.create(settings);

        database = client.getDatabase(mongoDatabase);


        responses = database.getCollection("responses");
        requests = database.getCollection("requests");
    }

    public void clear() {
        responses.drop();
        requests.drop();
    }

    public MongoCollection<Document> getResponses() {
        return responses;
    }

    public MongoCollection<Document> getRequests() {
        return requests;
    }

}
