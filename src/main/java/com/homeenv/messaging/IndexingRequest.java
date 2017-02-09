package com.homeenv.messaging;

public class IndexingRequest {
    private String path;
    private String hash;

    public IndexingRequest() {
    }

    public IndexingRequest(String path, String hash) {
        this.path = path;
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
