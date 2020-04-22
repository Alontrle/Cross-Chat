package com.tobiassteely.crosschat.slave;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.slave.event.MessageEvent;
import com.tobiassteely.crosschat.slave.event.PreMessageEvent;

import java.util.concurrent.ConcurrentHashMap;

public class SlaveMessageManager {

    private ConcurrentHashMap<String, Long> messageHistory;

    public SlaveMessageManager() {
        this.messageHistory = new ConcurrentHashMap<>();
    }

    public void processMessages() {
        for(String id : messageHistory.keySet()) {
            if(System.currentTimeMillis() - messageHistory.get(id) >= 120) {
                messageHistory.remove(id);
                CrossChat.getInstance().getMongoManager().getResponseManager().removeRecentID(id);
            }
        }
    }

    public void registerMessage(PreMessageEvent messageEvent) {
        messageHistory.put(messageEvent.getID(), System.currentTimeMillis());
    }

    public ConcurrentHashMap<String, Long> getMessageHistory() {
        return messageHistory;
    }
}
