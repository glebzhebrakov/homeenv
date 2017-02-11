package com.homeenv.messaging;

import java.util.Map;

public class IndexingResponse {
    private String path;
    private String hash;
    private String error;

    private Map<String, String> classificationResult;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getClassificationResult() {
        return classificationResult;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setClassificationResult(Map<String, String> classificationResult) {
        this.classificationResult = classificationResult;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
