package com.tanli.cloud.model;

public class DeployedApp {
    private String deploy_name;
    private String deploy_type;
    private String app_id;
    private String description;

    public String getDeploy_name() {
        return deploy_name;
    }

    public void setDeploy_name(String deploy_name) {
        this.deploy_name = deploy_name;
    }

    public String getDeploy_type() {
        return deploy_type;
    }

    public void setDeploy_type(String deploy_type) {
        this.deploy_type = deploy_type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
