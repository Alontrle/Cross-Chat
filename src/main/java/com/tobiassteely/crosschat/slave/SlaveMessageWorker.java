package com.tobiassteely.crosschat.slave;

import com.tobiassteely.crosschat.CrossChat;
import com.tobiassteely.crosschat.api.worker.Worker;

public class SlaveMessageWorker extends Worker {

    public SlaveMessageWorker() {
        super(5000);
    }

    @Override
    public Boolean runWorker(long start) {
        CrossChat.getInstance().getSlaveServer().getSlaveMessageManager().processMessages();
        return true;
    }

}
