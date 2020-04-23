package com.tobiassteely.crosschat.api.database.worker.request;

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

public class RequestWorker extends Worker {

    private List<Document> addQueue;
    private List<String> removeQueue;
    private HashMap<Document, Document> replaceQueue;
    private MongoCollection<Document> requests;

    public RequestWorker() {
        super(50);
        this.addQueue = new ArrayList<>();
        this.removeQueue = new ArrayList<>();
        this.replaceQueue = new HashMap<>();
        this.requests = CrossChat.getInstance().getMongo().getRequests();
    }

    @Override
    public Boolean runWorker(long start) {
        while(removeQueue.size() > 0) {
            String id = removeQueue.remove(0);

            this.requests.findOneAndDelete(new Document("id", id));
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }

                CrossChat.getInstance().getMongoManager().getRequestManager().removeRecentID(id);
            }).start();
        }
        while(replaceQueue.size() > 0) {
            Document oldDocument = replaceQueue.keySet().iterator().next();
            Document newDocument = replaceQueue.remove(oldDocument);

            this.requests.replaceOne(oldDocument, newDocument);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }

                String id = oldDocument.getString("id");
                CrossChat.getInstance().getMongoManager().getRequestManager().removeRecentID(id);
            }).start();
        }
        if(addQueue.size() > 0) {
            ArrayList<Document> temp = new ArrayList<>(addQueue);
            addQueue = new ArrayList<>();
            this.requests.insertMany(temp);
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
        removeQueue.add(id);
    }
}
