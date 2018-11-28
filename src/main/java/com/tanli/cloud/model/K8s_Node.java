package com.tanli.cloud.model;


public class K8s_Node {
    private K8s_Node_Metadata metadata;
    private K8s_Node_Status status;

    public K8s_Node(K8s_Node_Metadata metadata, K8s_Node_Status status) {
        this.metadata = metadata;
        this.status = status;
    }

    public K8s_Node_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(K8s_Node_Metadata metadata) {
        this.metadata = metadata;
    }

    public K8s_Node_Status getStatus() {
        return status;
    }

    public void setStatus(K8s_Node_Status status) {
        this.status = status;
    }
}

