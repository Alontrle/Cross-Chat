package com.tobiassteely.crosschat.api.database.worker.response;

import com.mongodb.client.MongoCollection;
import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.api.worker.Worker;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResponseWorker extends Worker {

    private List<Document> addQueue;
    private List<String> removeStringIDQueue;
    private HashMap<Document, Document> replaceQueue;
    private MongoCollection<Document> responses;

    public ResponseWorker() {
        super(50);
        this.addQueue = new ArrayList<>();
        this.removeStringIDQueue = new ArrayList<>();
        this.replaceQueue = new HashMap<>();
        this.responses = CrossChat.getInstance().getMongo().getResponses();
    }

    @Override
    public Boolean runWorker(long start) {
        while(removeStringIDQueue.size() > 0) {
            String id = removeStringIDQueue.remove(0);
            this.responses.findOneAndDelete(new Document("id", id));

            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}

                CrossChat.getInstance().getMongoManager().getResponseManager().removeRecentID(id);
                CrossChat.getInstance().getMasterServer().getMessageManager().remove(id);
            }).start();
        }
        while(replaceQueue.size() > 0) {
            Document oldDocument = replaceQueue.keySet().iterator().next();
            Document newDocument = replaceQueue.remove(oldDocument);


            this.responses.replaceOne(oldDocument, newDocument);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);

                    String id = oldDocument.getString("id");
                    CrossChat.getInstance().getMongoManager().getResponseManager().removeRecentID(id);
                    CrossChat.getInstance().getMasterServer().getMessageManager().remove(id);
                } catch (InterruptedException ignored) {
                }

            }).start();
        }
        if(addQueue.size() > 0) {
            ArrayList<Document> temp = new ArrayList<>(addQueue);
            addQueue = new ArrayList<>();
            this.responses.insertMany(temp);
        }
        return true;
    }

    public void addDocument(MongoDocument mongoDocument) {
        addQueue.add(mongoDocument.getFinalDocument());
    }

    public void replaceDocument(Document oldDocument, Document newDocument) {
        replaceQueue.put(oldDocument, newDocument);
    }

    public void removeDocument(String id) {
        removeStringIDQueue.add(id);
    }

}
