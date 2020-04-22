package com.tobiassteely.crosschat.master;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.Log;
import com.tobiassteely.crosschat.api.worker.Worker;

public class MasterMessageWorker extends Worker {

    public MasterMessageWorker() {
        super(5000);
    }

    @Override
    public Boolean runWorker(long start) {
        CrossChat.getInstance().getMasterServer().getMessageManager().processMessages();
        return true;
    }

}
