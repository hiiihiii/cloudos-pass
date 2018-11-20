package com.tanli.cloud.model.response;

import java.sql.Timestamp;

/**
 * Created by tanli on 2018/11/20 0020.
 */
public class Repository {
    private String repo_uuid;
    private String user_uuid;
    private String repo_name;
    private String repo_type;
    private String url;
    private Timestamp create_time;
    private Timestamp update_time;

    public Repository(String repo_uuid, String user_uuid, String repo_name, String repo_type, String url, Timestamp create_time, Timestamp update_time) {
        this.repo_uuid = repo_uuid;
        this.user_uuid = user_uuid;
        this.repo_name = repo_name;
        this.repo_type = repo_type;
        this.url = url;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    public Repository(){}

    public String getRepo_uuid() {
        return repo_uuid;
    }

    public void setRepo_uuid(String repo_uuid) {
        this.repo_uuid = repo_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_type() {
        return repo_type;
    }

    public void setRepo_type(String repo_type) {
        this.repo_type = repo_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
