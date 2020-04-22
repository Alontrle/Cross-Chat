package com.tobiassteely.crosschat.commandsystem.cmd;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Data;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.commandsystem.CommandObject;
import com.tobiassteely.crosschat.commandsystem.SpigotCommandEvent;

import java.util.ArrayList;

public class Help extends CommandObject {

    public Help() {
        super("Help", new String[] {"help", "?"}, "Core", "Sends this message", "help", "crosschat.help");
    }

    @Override
    public boolean run(String[] args, SpigotCommandEvent event) {
        if(event == null) {
            Log.sendMessage(0, new Data().getLineBreak());
            Log.sendMessage(0, "Commands");
            Log.sendMessage(0, new Data().getLineBreak());
            Log.sendMessage(0, " > help - Opens this menu");
            ArrayList<String> commandLines = new ArrayList<>();
            for(CommandObject commandObject : CrossChat.getInstance().getCommandManager().getCommands()) {
                commandLines.add(" > " + commandObject.getActivators()[0] + " - " + commandObject.getDescription());
            }
            for(String line : commandLines)
                Log.sendMessage(0, line);

            Log.sendMessage(0, new Data().getLineBreak());
        } else {
            ArrayList<String> helpCommandLines = new ArrayList<>();
            for(CommandObject commandObject : CrossChat.getInstance().getCommandManager().getCommands()) {
                    helpCommandLines.add("/" + commandObject.getUsage() + " - " + commandObject.getDescription());
            }

            if(helpCommandLines.size() > 0) {
                event.sendMessage(new Data().parseStringListNoDelimiter(0, helpCommandLines));
            }
        }
        return true;
    }



}
