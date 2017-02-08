package com.homeenv.messaging;

public class IndexingRequest {
    private String path;

    public IndexingRequest() {
    }

    public IndexingRequest(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
