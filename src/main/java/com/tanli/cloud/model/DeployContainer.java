package com.tanli.cloud.model;

import java.util.Map;

public class DeployContainer {
    private String[] args;
    private String[] command;
    private Map<String, String>[] env;
    private String image_source_url;
    private Map<String, Object>[] ports;
    private int replicas;
    private Map<String, Object> resources;
    private String serviceName;
    private String workingDir;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public Map<String, String>[] getEnv() {
        return env;
    }

    public void setEnv(Map<String, String>[] env) {
        this.env = env;
    }

    public String getImage_source_url() {
        return image_source_url;
    }

    public void setImage_source_url(String image_source_url) {
        this.image_source_url = image_source_url;
    }

    public Map<String, Object>[] getPorts() {
        return ports;
    }

    public void setPorts(Map<String, Object>[] ports) {
        this.ports = ports;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }
}
