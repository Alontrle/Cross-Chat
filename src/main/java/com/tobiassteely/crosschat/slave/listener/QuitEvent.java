package com.tobiassteely.crosschat.slave.listener;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.slave.event.PreMessageEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        Config settings = CrossChat.getInstance().getConfigManager().getConfig("settings.json");
        if(settings.getBoolean("spigotQuitEvent")) {
            String prefix = settings.getString("serverPrefix");
            String message = prefix + settings.getString("quitMessage").replace("%player%", event.getPlayer().getName());
            PreMessageEvent messageEvent = new PreMessageEvent(CrossChat.getInstance().getId(), PlaceholderAPI.setPlaceholders(event.getPlayer(), message));

            CrossChat.getInstance().getSlaveServer().getSlaveMessageManager().registerMessage(messageEvent.getID());

            CrossChat.getInstance().getMongoManager().getRequestWorker().addDocument(new MongoDocument(messageEvent.toDocument(), CrossChat.getInstance().getId(), "Master"));
        }
    }

}