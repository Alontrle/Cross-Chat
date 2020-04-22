package com.tobiassteely.crosschat;

import org.bukkit.plugin.java.JavaPlugin;

public class CrossChatSpigot extends JavaPlugin {

    private static CrossChatSpigot plugin = null;

    public static CrossChatSpigot getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        new CrossChat(false);
    }

}
