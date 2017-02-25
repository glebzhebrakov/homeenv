package com.homeenv;

public interface Constants {
    String REST_API_PREFIX = "/rest/api";

    interface RestApi{
        interface Indexing{
            String INDEX_METADATA = REST_API_PREFIX +"/indexing/metadata";
        }

        interface Classification{
            String CLASSIFICATIONS_ALL = REST_API_PREFIX +"/classifications/all";
            String CLASSIFICATIONS = REST_API_PREFIX +"/classifications/{from}/{to}";
            String CLASSIFICATIONS_BY_CLASS = REST_API_PREFIX +"/classifications/{class}/{from}/{to}";
            String CLASSES = REST_API_PREFIX+"/classes";
        }
    }
}
