package com.tobiassteely.crosschat;

import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.api.config.ConfigManager;
import com.tobiassteely.crosschat.api.database.Mongo;
import com.tobiassteely.crosschat.api.database.MongoManager;
import com.tobiassteely.crosschat.commandsystem.CommandManager;
import com.tobiassteely.crosschat.master.MasterServer;
import com.tobiassteely.crosschat.slave.SlaveServer;

public class CrossChat {

    private static CrossChat instance;

    public static CrossChat getInstance() {
        return instance;
    }

    private String id;
    private SlaveServer slaveServer = null;
    private MasterServer masterServer = null;
    private ConfigManager configManager;
    private Mongo mongo;
    private MongoManager mongoManager;
    private CommandManager commandManager;
    private boolean isMaster;

    public static void main(String[] args) {
        new CrossChat(true);
    }

    public CrossChat(boolean isMaster) {
        instance = this;
        this.isMaster = isMaster;
        this.configManager = new ConfigManager(isMaster);
        loadSettings();

        this.commandManager = new CommandManager();
        this.mongo = new Mongo();
        this.mongoManager = new MongoManager();
        this.mongoManager.start();
        if(isMaster) {
            Log.sendMessage(0, "Loading Master server");
            this.masterServer = new MasterServer();
            this.id = "Master";
        } else {
            Log.sendMessage(0,"Loading Slave server");
            this.slaveServer = new SlaveServer();
            this.id = configManager.getConfig("settings.json").getString("serverName");
        }
    }

    public void loadSettings() {
        Config settings = configManager.getConfig("settings.json");
        settings.loadDefault("mongodb-host", "127.0.0.1");
        settings.loadDefault("mongodb-username", "admin");
        settings.loadDefault("mongodb-password", "");
        settings.loadDefault("mongodb-db", "example");
        settings.loadDefault("mongodb-authdb", "admin");
        if(!isMaster()) {
            settings.loadDefault("serverName", "Server Name");
            settings.loadDefault("serverPrefix", "&8[&7Server&8] &f");
            settings.loadDefault("spigotDeathEvent", true);
            settings.loadDefault("spigotJoinEvent", true);
            settings.loadDefault("spigotQuitEvent", true);
            settings.loadDefault("deathMessage", "&c%player%&7 has died!");
            settings.loadDefault("joinMessage", "&8[&2+&8] &a%player%");
            settings.loadDefault("quitMessage", "&8[&4-&8] &c%player%");
            settings.loadDefault("customChat", true);
            settings.loadDefault("sendChatOnThisServer", false);
            settings.loadDefault("customChatFormat", "&7%displayname%&8:&f %message%");
        }
        settings.save();
    }

    public Mongo getMongo() {
        return mongo;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public MasterServer getMasterServer() {
        return masterServer;
    }

    public SlaveServer getSlaveServer() {
        return slaveServer;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public String getId() {
        return id;
    }
}
