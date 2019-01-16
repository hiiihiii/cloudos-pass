package com.tanli.cloud.model.Kubernetes;

import java.util.Map;

public class ReplicationControllerSpec {
    private int replicas;
    private Map<String, String> selector;
    private PodTemplateSpec template;

    public ReplicationControllerSpec () {}

    public ReplicationControllerSpec (int replicas, Map<String, String> selector, PodTemplateSpec pod) {
        this.replicas = replicas;
        this.selector = selector;
        this.template = pod;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public Map<String, String> getSelector() {
        return selector;
    }

    public void setSelector(Map<String, String> selector) {
        this.selector = selector;
    }

    public PodTemplateSpec getTemplate() {
        return template;
    }

    public void setTemplate(PodTemplateSpec template) {
        this.template = template;
    }
}
