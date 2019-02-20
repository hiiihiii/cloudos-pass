package com.tanli.cloud.model.response;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public class R_Deployment {
    private String deploy_name;
    private String status;
    private String source_type;
    private String source_name;
    private String update_time;
    private String deploy_uuid;
    private String user_uuid;

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getDeploy_name() {
        return deploy_name;
    }

    public void setDeploy_name(String deploy_name) {
        this.deploy_name = deploy_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDeploy_uuid() {
        return deploy_uuid;
    }

    public void setDeploy_uuid(String deploy_uuid) {
        this.deploy_uuid = deploy_uuid;
    }
}
