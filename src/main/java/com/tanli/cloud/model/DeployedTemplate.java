package com.tanli.cloud.model;

public class DeployedTemplate extends DeployedApp {

    private String containers;

    public String getContainers() {
        return containers;
    }

    public void setContainers(String containers) {
        this.containers = containers;
    }
}
