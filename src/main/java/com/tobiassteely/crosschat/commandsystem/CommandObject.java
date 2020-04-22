package com.tobiassteely.crosschat.commandsystem;

import com.tobiassteely.crosschat.api.manager.ManagerObject;

public class CommandObject extends ManagerObject {

    private String[] activators;
    private String module;
    private String description;
    private String usage;
    private String permission;

    public CommandObject(String name, String[] activators, String module, String description, String usage, String permission) {
        super(name, null);
        this.activators = activators;
        this.module = module;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
    }

    public String getName() {
        return getKey();
    }

    public String getDescription() {
        return description;
    }

    public String[] getActivators() {
        return activators;
    }

    public String getModule() {
        return module;
    }

    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        return usage;
    }

    public void setActivators(String[] activators) {
        this.activators = activators;
    }

    public boolean run(String[] args, SpigotCommandEvent event) {
        return false; // OVERRIDE
    }
}
