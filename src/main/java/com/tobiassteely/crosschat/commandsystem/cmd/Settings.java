package com.tobiassteely.crosschat.commandsystem.cmd;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Data;
import com.tobiassteely.crosschat.api.config.Config;
import com.tobiassteely.crosschat.api.config.ConfigManager;
import com.tobiassteely.crosschat.commandsystem.CommandObject;
import com.tobiassteely.crosschat.commandsystem.CommandResponse;
import com.tobiassteely.crosschat.commandsystem.SpigotCommandEvent;

import java.util.ArrayList;

public class Settings extends CommandObject {

    public Settings() {
        super("Settings", new String[] {"settings"}, "Core", "Manage the core settings", "settings", "crosschat.setting");
    }

    @Override
    public boolean run(String[] args, SpigotCommandEvent event) {
        Config config = CrossChat.getInstance().getConfigManager().getConfig("settings.json");
        if(event != null) {
            if(event.hasPermission("crosschat.settings")) {
                event.sendMessage("You do not have permissions for that!");
                return true;
            }
        }

        ArrayList<String> response = new ArrayList<>();
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("set")) {
                if(args.length >= 3) {
                    if(config.get(args[1]) != null) {
                        config.set(args[1], new Data().parseStringArrayNoDelimiter(2, args));
                        config.save();
                        response.add("Success! You have successfully set that value to " + new Data().parseStringArrayNoDelimiter(2, args) + ".");
                    } else {
                        response.add("Error! This is not an available setting. (Please keep in mind, it is case sensitive)");
                    }
                }
                if(response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("reset")) {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("botToken")) {
                        config.toJson().remove("botToken");
                        config.loadDefault("botToken", "example");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("guildID")) {
                        config.toJson().remove("guildID");
                        config.loadDefault("guildID", "example");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("commandPrefix")) {
                        config.toJson().remove("commandPrefix");
                        config.loadDefault("commandPrefix", "-");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("companyName")) {
                        config.toJson().remove("companyName");
                        config.loadDefault("companyName", "Company Name");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    } else if (args[1].equalsIgnoreCase("embedColor")) {
                        config.toJson().remove("embedColor");
                        config.loadDefault("embedColor", "#5a5a5a");
                        config.save();
                        response.add("Success! You have successfully reset that setting!");
                    }
                }
                if (response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("info")) {
                if(args.length == 2) {
                    if(config.get(args[1]) != null) {
                        response.add("Success! That value is set to `" + config.get(args[1]) + "`.");
                    } else {
                        response.add("Error! This is not an available setting. (Please keep in mind, it is case sensitive)");
                    }
                }
                if(response.size() == 0)
                    response.add("Error! Invalid input.");
            } else if(args[0].equalsIgnoreCase("list")) {
                response.add("Available Settings: botToken, guildID, commandPrefix, companyName, embedColor");
            }
        }
        if(response.size() == 0) {
            response.add("settings set <setting> <value> - Set the value of a setting\n");
            response.add("settings reset <setting> - Set the value of a setting to default\n");
            response.add("settings info <setting> - Show the value of a setting\n");
            response.add("settings list - Shows available settings");
        }

        CommandResponse commandResponse = new CommandResponse("Settings", 0, response);
        return true;
    }

}
