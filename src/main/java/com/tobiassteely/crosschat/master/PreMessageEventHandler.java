package com.tobiassteely.crosschat.master;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.api.database.worker.request.RequestEventHandler;
import com.tobiassteely.crosschat.slave.event.MessageEvent;
import org.bson.Document;

import java.util.ArrayList;

public class PreMessageEventHandler extends RequestEventHandler {

    @Override
    public void RequestEventHandler(MongoDocument document) {
        if(document.getDestination().equalsIgnoreCase("Master")) {
            if(document.getDocument().getString("type").equals("PreMessageEvent")) {
                MessageEvent messageEvent = new MessageEvent("Master", document.getDocument().getString("data"), document.getDocument().getString("id"));

                CrossChat.getInstance().getMongoManager().getResponseWorker().addDocument(new MongoDocument(messageEvent.toDocument(), "Master", "Public"));
            }
        }
    }

}
