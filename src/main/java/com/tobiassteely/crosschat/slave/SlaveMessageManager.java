package com.tobiassteely.crosschat.slave;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
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
            if(System.currentTimeMillis() - messageHistory.get(id) >= 120000) {
                messageHistory.remove(id);
                CrossChat.getInstance().getMongoManager().getResponseManager().removeRecentID(id);
            }
        }
    }

    public void registerMessage(String id) {
        messageHistory.put(id, System.currentTimeMillis());
        Log.sendMessage(0, "* " + id);
        CrossChat.getInstance().getMongoManager().getResponseManager().addRecentID(id);
    }

}
