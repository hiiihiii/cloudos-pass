package com.tanli.cloud.model;

/**
 * Created by tanli on 2019/2/13 0013.
 */
public class K8s_Pod {
    private String uuid;
    private String rc_uuid;
    private String svc_uuid;
    private String name;
    private String label;
    private String namespace;
    private String hostIP;
    private String podIP;
    private int restartCount;
    private String image;
    private String status;
    private String update_time;

    public K8s_Pod() {}

    public K8s_Pod(String uuid, String rc_uuid, String svc_uuid, String name, String label, String namespace, String hostIP, String podIP, int restartCount, String image, String status, String update_time) {
        this.uuid = uuid;
        this.rc_uuid = rc_uuid;
        this.svc_uuid = svc_uuid;
        this.name = name;
        this.label = label;
        this.namespace = namespace;
        this.hostIP = hostIP;
        this.podIP = podIP;
        this.restartCount = restartCount;
        this.image = image;
        this.status = status;
        this.update_time = update_time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRc_uuid() {
        return rc_uuid;
    }

    public void setRc_uuid(String rc_uuid) {
        this.rc_uuid = rc_uuid;
    }

    public String getSvc_uuid() {
        return svc_uuid;
    }

    public void setSvc_uuid(String svc_uuid) {
        this.svc_uuid = svc_uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getPodIP() {
        return podIP;
    }

    public void setPodIP(String podIP) {
        this.podIP = podIP;
    }

    public int getRestartCount() {
        return restartCount;
    }

    public void setRestartCount(int restartCount) {
        this.restartCount = restartCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
