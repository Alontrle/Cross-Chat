package com.tobiassteely.crosschat.api.database;

import com.tobiassteely.crosschat.api.database.worker.request.RequestManager;
import com.tobiassteely.crosschat.api.database.worker.request.RequestWorker;
import com.tobiassteely.crosschat.api.database.worker.response.ResponseManager;
import com.tobiassteely.crosschat.api.database.worker.response.ResponseWorker;

public class MongoManager {

    private RequestWorker requestWorker;
    private ResponseWorker responseWorker;

    private RequestManager requestManager;
    private ResponseManager responseManager;

    public MongoManager() {
        this.requestWorker = new RequestWorker();
        this.responseWorker = new ResponseWorker();

        this.requestManager = new RequestManager();
        this.responseManager = new ResponseManager();
    }

    public void start() {
        requestWorker.start();
        responseWorker.start();

        requestManager.start();
        responseManager.start();

        requestManager.loadManager();
        responseManager.loadManager();
    }

    public ResponseManager getResponseManager() {
        return responseManager;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public RequestWorker getRequestWorker() {
        return requestWorker;
    }

    public ResponseWorker getResponseWorker() {
        return responseWorker;
    }
}
