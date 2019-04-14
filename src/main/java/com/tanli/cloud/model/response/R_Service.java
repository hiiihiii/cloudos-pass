package com.tanli.cloud.model.response;

import java.util.List;

/**
 * Created by tanli on 2019/2/23 0023.
 */
public class R_Service {
    private String deploy_uuid;
    private String uuid;
    private String name;
    private String status;
    private List<String> ips;
    private int replicas;

    public String getDeploy_uuid() {
        return deploy_uuid;
    }

    public void setDeploy_uuid(String deploy_uuid) {
        this.deploy_uuid = deploy_uuid;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
