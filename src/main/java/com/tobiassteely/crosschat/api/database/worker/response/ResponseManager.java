package com.tobiassteely.crosschat.api.database.worker.response;

import com.mongodb.client.MongoCollection;
import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.api.worker.Worker;
import org.bson.Document;

import java.util.List;
import java.util.Vector;

public class ResponseManager extends Worker {

    private MongoCollection<Document> responses;
    private List<ResponseEventHandler> events;
    private List<String> recentIDs;

    public ResponseManager() {
        super(50);
        this.events = new Vector<>();
        this.recentIDs = new Vector<>();
        this.responses = CrossChat.getInstance().getMongo().getResponses();
    }

    public void registerHandler(ResponseEventHandler eventHandler) {
        events.add(eventHandler);
    }

    public Boolean runWorker(long start) {
        for (Document doc : responses.find(new Document("destination", CrossChat.getInstance().getId()).append("loaded", false))) {
            new Thread(() -> {
                if(!recentIDs.contains(doc.getString("id"))) {
                    recentIDs.add(doc.getString("id"));
                    for (ResponseEventHandler eventHandler : events) {
                        eventHandler.ResponseEventHandler(new MongoDocument(new Document(doc))); // PASSES DOCUMENT
                    }
                }
            }).start();
        }
        for (Document doc : responses.find(new Document("destination", "Public").append("loaded", false))) {
            new Thread(() -> {
                if(!recentIDs.contains(doc.getString("id"))) {
                    recentIDs.add(doc.getString("id"));
                    for (ResponseEventHandler eventHandler : events) {
                        eventHandler.ResponseEventHandler(new MongoDocument(new Document(doc))); // PASSES DOCUMENT
                    }
                }
            }).start();
        }
        return true;
    }

    public void loadManager() {
        for (Document doc : responses.find(new Document("destination", CrossChat.getInstance().getId()))) {
            for (ResponseEventHandler eventHandler : events) {
                eventHandler.ResponseEventHandler(new MongoDocument(doc)); // PASSES DOCUMENT
            }
        }
    }

    public void removeRecentID(String id) {
        this.recentIDs.remove(id);
    }
}
