package com.tobiassteely.crosschat.commandsystem.cmd;

import com.tobiassteely.crosschat.commandsystem.CommandObject;
import com.tobiassteely.crosschat.commandsystem.SpigotCommandEvent;

public class End extends CommandObject {

    public End() {
        super("End", new String[] {"end"}, "Core", "Shuts the bot down", "end", "none");
    }

    @Override
    public boolean run(String[] args, SpigotCommandEvent event) {
        if (event == null)
            System.exit(0);
        else {
            event.sendMessage("You cannot turn off a slave instance.");
        }
        return true;
    }

}
