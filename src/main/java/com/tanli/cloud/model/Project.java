package com.tanli.cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2019/3/26 0026.
 */
public class Project {

    private String project_name;

    @JsonProperty("public")
    private int pub;

    public Project(String project_name, int pub) {
        this.project_name = project_name;
        this.pub = pub;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getPub() {
        return pub;
    }

    public void setPub(int pub) {
        this.pub = pub;
    }
}
