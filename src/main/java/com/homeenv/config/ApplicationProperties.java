package com.homeenv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "homeenv", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Indexing indexing = new Indexing();

    public Indexing getIndexing() {
        return indexing;
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
}
