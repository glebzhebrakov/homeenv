package com.homeenv;

public interface Constants {
    String REST_API_PREFIX = "/rest/api";

    interface RestApi{
        interface Indexing{
            String INDEX_METADATA = REST_API_PREFIX +"/indexing/metadata";
        }
    }
}
