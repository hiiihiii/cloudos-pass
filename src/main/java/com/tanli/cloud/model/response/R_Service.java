package com.tanli.cloud.model.response;

import java.util.List;

/**
 * Created by tanli on 2019/2/23 0023.
 */
public class R_Service {
    private String uuid;
    private String name;
    private String status;
    private List<String> ips;

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
