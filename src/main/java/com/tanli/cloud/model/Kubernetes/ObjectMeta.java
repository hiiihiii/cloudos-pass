package com.tanli.cloud.model.Kubernetes;

import java.util.Map;

public class ObjectMeta {
    private String name;
    private String namespace;
    private Map<String, String> labels;


    public ObjectMeta() {}

    public ObjectMeta(String name, String namespace, Map<String, String> labels) {
        this.name = name;
        this.namespace = namespace;
        this.labels = labels;
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

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
