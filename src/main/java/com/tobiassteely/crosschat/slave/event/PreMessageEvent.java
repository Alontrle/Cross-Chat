package com.tobiassteely.crosschat.slave.event;

import com.tobiassteely.crosschat.api.database.worker.response.RequestObject;

import java.util.UUID;

public class PreMessageEvent extends RequestObject {

    public PreMessageEvent(String origin, String message) {
        super(origin, "Master", "PreMessageEvent", message, UUID.randomUUID().toString());
    }

}
