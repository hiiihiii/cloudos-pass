package com.tanli.cloud.model;

import java.util.Map;

public class DeployContainer {
    private String[] args;
    private String[] command;
    private Map<String, String>[] env;
    private String image_source_url;
    private Map<String, Object>[] ports;
    private int replicas;
    private Map<String, String> limits;
    private Map<String, String> requests;
    private String serviceName;
    private String workingDir;
    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

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

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }

    public Map<String, String> getRequests() {
        return requests;
    }

    public void setRequests(Map<String, String> requests) {
        this.requests = requests;
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
