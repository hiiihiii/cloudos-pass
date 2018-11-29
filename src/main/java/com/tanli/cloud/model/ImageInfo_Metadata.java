package com.tanli.cloud.model;

import java.util.List;
import java.util.Map;

public class ImageInfo_Metadata {
    private String volume;
    private String cmd;
    private String[] cmdParams;
    private List<Map<String, String>> env;
    private List<Map<String, String>> ports;
    private Map<String, String> requests;
    private Map<String, String> limits;

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

    public List<Map<String, String>> getEnv() {
        return env;
    }

    public void setEnv(List<Map<String, String>> env) {
        this.env = env;
    }

    public List<Map<String, String>> getPorts() {
        return ports;
    }

    public void setPorts(List<Map<String, String>> ports) {
        this.ports = ports;
    }

    public Map<String, String> getRequests() {
        return requests;
    }

    public void setRequests(Map<String, String> requests) {
        this.requests = requests;
    }

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }
}
