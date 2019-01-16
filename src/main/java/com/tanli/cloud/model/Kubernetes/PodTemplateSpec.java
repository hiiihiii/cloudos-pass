package com.tanli.cloud.model.Kubernetes;

public class PodTemplateSpec {
    private ObjectMeta metadata;
    private PodSpec spec;

    public PodTemplateSpec(ObjectMeta metadata, PodSpec spec) {
        this.metadata = metadata;
        this.spec = spec;
    }

    public PodTemplateSpec() {
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public void setMetadata(ObjectMeta metadata) {
        this.metadata = metadata;
    }

    public PodSpec getSpec() {
        return spec;
    }

    public void setSpec(PodSpec spec) {
        this.spec = spec;
    }
}
