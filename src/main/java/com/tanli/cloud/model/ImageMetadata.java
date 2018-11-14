package com.tanli.cloud.model;

import java.util.ArrayList;

public class ImageMetadata {
    private String volume;
    private String cmd;
    private ArrayList<String> cmdParams;
    private ArrayList<Env> env;
    private ArrayList<Port> ports;
}

class Env {
    private String envKey;
    private String envValue;

    public Env(String envKey, String envValue) {
        this.envKey = envKey;
        this.envValue = envValue;
    }

    public String getEnvKey() {
        return envKey;
    }

    public void setEnvKey(String envKey) {
        this.envKey = envKey;
    }

    public String getEnvValue() {
        return envValue;
    }

    public void setEnvValue(String envValue) {
        this.envValue = envValue;
    }
}

class Port {
    private String portName;
    private String protocol;
    private String containerPort;
    private String port;
    private String targetPort;

    public Port(String portName, String protocol, String containerPort, String port, String targetPort) {
        this.portName = portName;
        this.protocol = protocol;
        this.containerPort = containerPort;
        this.port = port;
        this.targetPort = targetPort;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }
}