package com.tobiassteely.crosschat.commandsystem;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotCommandEvent {

    private CommandSender sender;

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    public void sendMessage(String response) {

    }

}
