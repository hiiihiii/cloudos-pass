package com.tanli.cloud.model;

public class Deployment {
    private String deploy_uuid;
    private String deploy_name;
    private String user_uuid;
    private String deploy_type;
    private String template_uuid;
    private String image_uuid;
    private String description;
    private String create_time;
    private String update_time;

    public String getDeploy_uuid() {
        return deploy_uuid;
    }

    public void setDeploy_uuid(String deploy_uuid) {
        this.deploy_uuid = deploy_uuid;
    }

    public String getDeploy_name() {
        return deploy_name;
    }

    public void setDeploy_name(String deploy_name) {
        this.deploy_name = deploy_name;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getDeploy_type() {
        return deploy_type;
    }

    public void setDeploy_type(String deploy_type) {
        this.deploy_type = deploy_type;
    }

    public String getTemplate_uuid() {
        return template_uuid;
    }

    public void setTemplate_uuid(String template_uuid) {
        this.template_uuid = template_uuid;
    }

    public String getImage_uuid() {
        return image_uuid;
    }

    public void setImage_uuid(String image_uuid) {
        this.image_uuid = image_uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
