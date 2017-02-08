package com.homeenv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "homeenv", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Indexing indexing = new Indexing();

    private final Rabbit rabbit = new Rabbit();

    public Indexing getIndexing() {
        return indexing;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public static class Indexing {
        private String path;
        private Boolean recursive;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Boolean getRecursive() {
            return recursive;
        }

        public void setRecursive(Boolean recursive) {
            this.recursive = recursive;
        }
    }

    public static class Rabbit {
        private String queueIndexingRequests;
        private String queueIndexingResponses;
        private String exchangeName;

        public String getQueueIndexingRequests() {
            return queueIndexingRequests;
        }

        public void setQueueIndexingRequests(String queueIndexingRequests) {
            this.queueIndexingRequests = queueIndexingRequests;
        }

        public String getQueueIndexingResponses() {
            return queueIndexingResponses;
        }

        public void setQueueIndexingResponses(String queueIndexingResponses) {
            this.queueIndexingResponses = queueIndexingResponses;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public void setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
        }

    }
}
