package com.tanli.cloud.model.Kubernetes;

import java.util.Map;

public class ServiceSpec {
    private ServicePort[] ports;
    private Map<String, String> selector;
    private String type;

    public ServicePort[] getPorts() {
        return ports;
    }

    public void setPorts(ServicePort[] ports) {
        this.ports = ports;
    }

    public Map<String, String> getSelector() {
        return selector;
    }

    public void setSelector(Map<String, String> selector) {
        this.selector = selector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
