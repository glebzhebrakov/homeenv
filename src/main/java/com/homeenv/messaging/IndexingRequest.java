package com.homeenv.messaging;

public class IndexingRequest {
    private String path;
    private String hash;
    private String indexingDirectory;

    public IndexingRequest() {
    }

    public IndexingRequest(String path, String hash, String indexingDirectory) {
        this.path = path;
        this.hash = hash;
        this.indexingDirectory = indexingDirectory;
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

    public String getIndexingDirectory() {
        return indexingDirectory;
    }

    public void setIndexingDirectory(String indexingDirectory) {
        this.indexingDirectory = indexingDirectory;
    }
}
