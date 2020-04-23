package com.tobiassteely.crosschat.slave.listener;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.api.database.MongoDocument;
import com.tobiassteely.crosschat.slave.event.PreMessageEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Config settings = CrossChat.getInstance().getConfigManager().getConfig("settings.json");
        if(settings.getBoolean("spigotDeathEvent")) {
            String prefix = settings.getString("serverPrefix");
            String message = prefix + settings.getString("deathMessage").replace("%player%", event.getEntity().getName());
            PreMessageEvent messageEvent = new PreMessageEvent(CrossChat.getInstance().getId(), PlaceholderAPI.setPlaceholders(event.getEntity(), message));

            CrossChat.getInstance().getSlaveServer().getSlaveMessageManager().registerMessage(messageEvent.getID());

            CrossChat.getInstance().getMongoManager().getRequestWorker().addDocument(new MongoDocument(messageEvent.toDocument(), CrossChat.getInstance().getId(), "Master"));
        }
    }
}
