package com.tobiassteely.crosschat.api;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.CrossChatBungee;
import com.tobiassteely.crosschat.CrossChatSpigot;
import com.tobiassteely.crosschat.api.config.ConfigManager;

public class Log {

    private static boolean verboseMessages = true;

    public Log() {
        verboseMessages = CrossChat.getInstance().getConfigManager().getConfig("settings.json").getBoolean("verbose");
    }

    public static void sendMessage(int status, String msg) {
        if(CrossChat.getInstance().isMaster()) {
            if(status == 0) {
                if(CrossChatBungee.getPlugin() == null) {
                    System.out.println("[INFO] " + msg);
                } else {
                    CrossChatBungee.getPlugin().getLogger().info(msg);
                }
            } else if(status == 1) {
                if(CrossChatBungee.getPlugin() == null) {
                    System.out.println("[WARN] " + msg);
                } else {
                    CrossChatBungee.getPlugin().getLogger().warning(msg);
                }
            } else if(status == 2) {
                if(CrossChatBungee.getPlugin() == null) {
                    System.out.println("[ERROR] " + msg);
                } else {
                    CrossChatBungee.getPlugin().getLogger().warning("ERROR : " + msg);
                }
            } else if(status == 3) {
                if (verboseMessages) {
                    if(CrossChatBungee.getPlugin() == null) {
                        System.out.println("[VERBOSE] " + msg);
                    } else {
                        CrossChatBungee.getPlugin().getLogger().info("VERBOSE : " + msg);
                    }
                }
            }
        } else {
            if(status == 0)
                CrossChatSpigot.getPlugin().getLogger().info(msg);
            else if(status == 1)
                CrossChatSpigot.getPlugin().getLogger().warning(msg);
            else if(status == 2)
                CrossChatSpigot.getPlugin().getLogger().warning("ERROR : " + msg);
            else if(status == 3)
                if(verboseMessages)
                    CrossChatSpigot.getPlugin().getLogger().warning("VERBOSE : " + msg);
        }
    }

    public static void toggleVerbose(boolean toggle) {
        verboseMessages = toggle;
    }

}
