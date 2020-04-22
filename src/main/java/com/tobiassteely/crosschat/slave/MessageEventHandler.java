package com.tobiassteely.crosschat.slave;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.api.database.worker.request.RequestEventHandler;
import com.tobiassteely.crosschat.api.database.worker.response.ResponseEventHandler;
import com.tobiassteely.crosschat.slave.event.MessageEvent;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class MessageEventHandler extends ResponseEventHandler {

    @Override
    public void ResponseEventHandler(MongoDocument document) {
        if(document.getDestination().equalsIgnoreCase("Public")) {
            if(document.getDocument().getString("type").equals("MessageEvent")) {
                if(!CrossChat.getInstance().getSlaveServer().getSlaveMessageManager().getMessageHistory().containsKey(document.getDocument().getString("id"))) {
                    String text = document.getDocument().getString("data");
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', text));
                }
            }
        }
    }
}
