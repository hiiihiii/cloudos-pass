package com.tanli.cloud.model;

public class ImageMetadata {
    private String volume;
    private String cmd;
    private String[] cmdParams;
    private Env[] env;
    private Port[] ports;
    private Resource requests;
    private Resource limits;

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String[] getCmdParams() {
        return cmdParams;
    }

    public void setCmdParams(String[] cmdParams) {
        this.cmdParams = cmdParams;
    }

    public Env[] getEnv() {
        return env;
    }

    public void setEnv(Env[] env) {
        this.env = env;
    }

    public Port[] getPorts() {
        return ports;
    }

    public void setPorts(Port[] ports) {
        this.ports = ports;
    }

    public Resource getRequests() {
        return requests;
    }

    public void setRequests(Resource requests) {
        this.requests = requests;
    }

    public Resource getLimits() {
        return limits;
    }

    public void setLimits(Resource limits) {
        this.limits = limits;
    }
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

class Resource {
    private String cpu;
    private String memory;

    public Resource(String cpu, String memory) {
        this.cpu = cpu;
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}