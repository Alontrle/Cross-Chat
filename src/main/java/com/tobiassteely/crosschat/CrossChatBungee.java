package com.tobiassteely.crosschat;

import net.md_5.bungee.api.plugin.Plugin;

public class CrossChatBungee extends Plugin {

    private static CrossChatBungee plugin = null;

    public static CrossChatBungee getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        new CrossChat(true);
    }

}
