package com.tobiassteely.crosschat.commandsystem;

import com.tobiassteely.crosschat.api.Data;
import com.tobiassteely.crosschat.api.Log;

import java.util.ArrayList;
import java.util.List;

public class CommandResponse {

    private String title = null;
    private int status;
    private List<String> response;

    public CommandResponse(String title, int status, List<String> response) {
        this.title = title;
        this.status = status;
        this.response = response;
    }

    public CommandResponse(String title, int status) {
        this.title = title;
        this.status = status;
        this.response = new ArrayList<>();
    }

    public List<String> getResponse() {
        return response;
    }

    public void sendResponse(SpigotCommandEvent event) {
        if(event == null) {
            if(title == null) {
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response) {
                    Log.sendMessage(0, line);
                }
                Log.sendMessage(0, new Data().getLineBreak());
            } else {
                Log.sendMessage(0, new Data().getLineBreak());
                Log.sendMessage(0, title);
                Log.sendMessage(0, new Data().getLineBreak());
                for(String line : response) {
                    Log.sendMessage(0, line);
                }
                Log.sendMessage(0, new Data().getLineBreak());
            }
        } else {
            event.sendMessage(new Data().parseStringListNoDelimiter(0, response));
        }
    }

}
