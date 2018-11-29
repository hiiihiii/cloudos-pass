package com.tanli.cloud.model;

import java.util.Map;

public class ImageInfo_Request {
    private String app_id;
    private String repo_id;
    private String user_id;
    private String appName;
    private String type;
    private String[] versions;
    private String appTag;
    private String description;
    private Map<String, String> v_description;
    private Map<String, String> logo_url;
    private Map<String, String> source_url;
    private Map<String, String> metadata;
    private Map<String, String> createType;
    private String create_time;
    private String update_time;
}
