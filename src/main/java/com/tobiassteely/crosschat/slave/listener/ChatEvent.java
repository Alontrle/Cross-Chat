package com.tobiassteely.crosschat.slave.listener;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.slave.event.PreMessageEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Config settings = CrossChat.getInstance().getConfigManager().getConfig("settings.json");
        if(settings.getBoolean("customChat")) {
            String prefix = settings.getString("serverPrefix");
            String format = settings.getString("customChatFormat");
            String message = format.replace("%player%", event.getPlayer().getName()).replace("%displayname%", event.getPlayer().getDisplayName()).replace("%message%", event.getMessage());

            if(settings.getBoolean("sendChatOnThisServer")) {
                event.setCancelled(true);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            PreMessageEvent messageEvent = new PreMessageEvent(CrossChat.getInstance().getId(), PlaceholderAPI.setPlaceholders(event.getPlayer(), prefix + message));
            CrossChat.getInstance().getSlaveServer().getSlaveMessageManager().registerMessage(messageEvent);
            CrossChat.getInstance().getMongoManager().getRequestWorker().addDocument(new MongoDocument(messageEvent.toDocument(), CrossChat.getInstance().getId(), "Master"));
        }
    }
}
