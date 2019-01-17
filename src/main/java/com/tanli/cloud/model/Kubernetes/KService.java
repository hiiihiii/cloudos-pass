package com.tanli.cloud.model.Kubernetes;

public class KService {
    private String apiVersion;
    private String kind;
    private ObjectMeta metadata;
    private ServiceSpec spec;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public void setMetadata(ObjectMeta metadata) {
        this.metadata = metadata;
    }

    public ServiceSpec getSpec() {
        return spec;
    }

    public void setSpec(ServiceSpec spec) {
        this.spec = spec;
    }
}
