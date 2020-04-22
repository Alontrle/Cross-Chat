package com.tobiassteely.crosschat.master;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.CrossChatBungee;
import com.tobiassteely.crosschat.commandsystem.CommandWorker;
import com.tobiassteely.crosschat.slave.MessageEventHandler;

public class MasterServer {

    private CommandWorker commandWorker;
    private MasterMessageManager messageManager;
    private MasterMessageWorker messageWorker;

    public MasterServer() {
        this.messageManager = new MasterMessageManager();
        this.messageWorker = new MasterMessageWorker();

        if(CrossChatBungee.getPlugin() == null) {
            this.commandWorker = new CommandWorker();
        }

        CrossChat.getInstance().getMongoManager().getRequestManager().registerHandler(new PreMessageEventHandler());
        //CrossChat.getInstance().getMongo().clear();

        this.commandWorker.start();
        this.messageWorker.start();
    }

    public MasterMessageManager getMessageManager() {
        return messageManager;
    }

    public MasterMessageWorker getMessageWorker() {
        return messageWorker;
    }

    public CommandWorker getCommandWorker() {
        return commandWorker;
    }
}
