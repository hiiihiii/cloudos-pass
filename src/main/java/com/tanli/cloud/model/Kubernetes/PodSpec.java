package com.tanli.cloud.model.Kubernetes;

public class PodSpec {
    private Container[] containers;
    private String restartPolicy;

    public PodSpec() {}
    public PodSpec(Container[] containers, String restartPolicy) {
        this.containers = containers;
        this.restartPolicy = restartPolicy;
    }

    public Container[] getContainers() {
        return containers;
    }

    public void setContainers(Container[] containers) {
        this.containers = containers;
    }

    public String getRestartPolicy() {
        return restartPolicy;
    }

    public void setRestartPolicy(String restartPolicy) {
        this.restartPolicy = restartPolicy;
    }
}
