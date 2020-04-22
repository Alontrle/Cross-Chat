package com.tobiassteely.crosschat.slave;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.CrossChatSpigot;
import com.tobiassteely.crosschat.slave.listener.ChatEvent;
import com.tobiassteely.crosschat.slave.listener.DeathEvent;
import com.tobiassteely.crosschat.slave.listener.JoinEvent;
import com.tobiassteely.crosschat.slave.listener.QuitEvent;

public class SlaveServer {

    private SlaveMessageManager slaveMessageManager;
    private SlaveMessageWorker slaveMessageWorker;

    public SlaveServer() {
        this.slaveMessageManager = new SlaveMessageManager();
        this.slaveMessageWorker = new SlaveMessageWorker();
        this.slaveMessageWorker.start();


        CrossChatSpigot.getPlugin().getServer().getPluginManager().registerEvents(new JoinEvent(), CrossChatSpigot.getPlugin());
        CrossChatSpigot.getPlugin().getServer().getPluginManager().registerEvents(new QuitEvent(), CrossChatSpigot.getPlugin());
        CrossChatSpigot.getPlugin().getServer().getPluginManager().registerEvents(new DeathEvent(), CrossChatSpigot.getPlugin());
        CrossChatSpigot.getPlugin().getServer().getPluginManager().registerEvents(new ChatEvent(), CrossChatSpigot.getPlugin());

        CrossChat.getInstance().getMongoManager().getResponseManager().registerHandler(new MessageEventHandler());
    }

    public SlaveMessageManager getSlaveMessageManager() {
        return slaveMessageManager;
    }

    public SlaveMessageWorker getSlaveMessageWorker() {
        return slaveMessageWorker;
    }
}
