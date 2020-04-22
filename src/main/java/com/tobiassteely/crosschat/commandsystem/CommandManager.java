package com.tobiassteely.crosschat.commandsystem;

import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.manager.ManagerCache;
import com.tobiassteely.crosschat.api.manager.ManagerParent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager extends ManagerParent {

    public CommandManager() {
        addCache("activators", new ManagerCache());
    }

    public void registerCommand(CommandObject commandObject) {
        addObject(commandObject);
        if(!getCache("modules").isCached(commandObject.getModule()))
            getCache("modules").putObject(commandObject.getModule(), new ArrayList<>());

        ArrayList<Object> commands = (ArrayList<Object>)getCache("modules").getObject(commandObject.getModule());
        commands.add(commandObject);

        for(String activator : commandObject.getActivators())
            getCache("activators").putObject(activator.toLowerCase(), commandObject);
    }

    public boolean runCommand(String input, SpigotCommandEvent event) {
        String command = input.split(" ")[0].toLowerCase();
        String[] args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);

        if(getCache("activators").isCached(command)) {
            CommandObject commandObject = (CommandObject)getCache("activators").getObject(command);
            if(event != null) {
                if(!event.hasPermission(commandObject.getPermission())) {
                    return true;
                }
            }
            new Thread(() -> {
                if(!commandObject.run(args, event)) {
                    if(event == null) {
                        Log.sendMessage(2, "Command registered, but not running properly.");
                    }
                }
            }).start();
                return true;
        } else {
            if(event == null) {
                Log.sendMessage(2, "Unknown command, type \"?\" for a list of available commands.");
                return false;
            }
            return true;
        }
    }

    public ArrayList<CommandObject> getCommands() {
        ArrayList<CommandObject> commands = new ArrayList<>();
        for(Object object : getList()) {
            commands.add((CommandObject)object);
        }
        return commands;
    }

}
