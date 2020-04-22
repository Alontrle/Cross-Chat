package com.tobiassteely.crosschat.api.manager;


import com.tobiassteely.crosschat.api.config.Config;
import org.bson.Document;
import org.json.simple.JSONObject;

public class ManagerObject {

    private String key;
    private Config config;

    public ManagerObject(String key, Config config) {
        this.key = key;
        this.config = config;
    }

    public String getKey() {
        return key;
    }

    public Config getConfig() {
        return config;
    }

    public JSONObject toJson() {
        return null; // OVERRIDE
    }

    public Document toDocument() {
        return null; // OVERRIDE
    }
}
