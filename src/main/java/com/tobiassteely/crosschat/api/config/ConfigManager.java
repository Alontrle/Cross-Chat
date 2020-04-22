package com.tobiassteely.crosschat.api.config;

import com.tobiassteely.crosschat.CrossChatBungee;
import com.tobiassteely.crosschat.CrossChatSpigot;
import com.tobiassteely.crosschat.api.manager.ManagerParent;

public class ConfigManager extends ManagerParent {

    private boolean isMaster;

    public ConfigManager(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public Config getConfig(String configName) {

        if(getObjectWithKey(configName) == null) {
            Config config;
            if(isMaster) {
                if(CrossChatBungee.getPlugin() == null) {
                    config = new Config(configName);
                } else {
                    config = new Config(CrossChatBungee.getPlugin().getDataFolder() + System.getProperty("file.separator") + configName);
                }
            } else  {
                config = new Config(CrossChatSpigot.getPlugin().getDataFolder() + System.getProperty("file.separator") + configName);
            }
            addObject(config);
            return config;
        }

        if(isMaster) {
            if(CrossChatBungee.getPlugin() == null) {
                return (Config) getObjectWithKey(configName);
            } else {
                return (Config) getObjectWithKey(CrossChatBungee.getPlugin().getDataFolder() + System.getProperty("file.separator") + configName);
            }
        } else {
            return (Config) getObjectWithKey(CrossChatSpigot.getPlugin().getDataFolder() + System.getProperty("file.separator") + configName);
        }
    }

}
