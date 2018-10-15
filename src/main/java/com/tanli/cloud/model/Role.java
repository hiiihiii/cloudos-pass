package com.tanli.cloud.model;

import java.sql.Timestamp;

/**
 * Created by tanli on 2018/10/15 0015.
 */
public class Role {
    private String uuid;
    private String role_name;
    private String description;
    private Timestamp create_time;
    private Timestamp update_time;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return role_name;
    }

    public void setName(String name) {
        this.role_name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
