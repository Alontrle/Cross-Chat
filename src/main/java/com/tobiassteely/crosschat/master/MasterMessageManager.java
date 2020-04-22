package com.tobiassteely.crosschat.master;

import com.tobiassteely.crosschat.CrossChat;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class MasterMessageManager {

    private ConcurrentHashMap<String, Long> messageHistory;

    public MasterMessageManager() {
        this.messageHistory = new ConcurrentHashMap<>();
    }

    public void processMessages() {
        for(String id : messageHistory.keySet()) {
            if(System.currentTimeMillis() - messageHistory.get(id) >= 30) {
                CrossChat.getInstance().getMongoManager().getResponseWorker().removeDocument(id);
            }
        }
    }

    public void remove(String id) {
        messageHistory.remove(id);
    }

}
