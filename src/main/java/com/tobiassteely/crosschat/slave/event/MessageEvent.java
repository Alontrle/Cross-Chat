package com.tobiassteely.crosschat.slave.event;

import com.tobiassteely.crosschat.api.database.worker.request.ResponseObject;
import org.bson.Document;

import java.util.UUID;

public class MessageEvent extends ResponseObject {

    public MessageEvent(String origin, String data, String id) {
        super(origin, "Public", "MessageEvent", data, id);
    }

}
