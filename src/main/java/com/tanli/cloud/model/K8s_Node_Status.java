package com.tanli.cloud.model;

import java.util.List;
import java.util.Map;

public class K8s_Node_Status {
    private Map<String, String> capacity;
    private Map<String, String> allocatable;
    private List<Map<String, String>> conditions;
    private List<Map<String, String>> addresses;

    public List<Map<String, String>> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Map<String, String>> addresses) {
        this.addresses = addresses;
    }

    public Map<String, String> getCapacity() {
        return capacity;
    }

    public void setCapacity(Map<String, String> capacity) {
        this.capacity = capacity;
    }

    public Map<String, String> getAllocatable() {
        return allocatable;
    }

    public void setAllocatable(Map<String, String> allocatable) {
        this.allocatable = allocatable;
    }

    public List<Map<String, String>> getConditions() {
        return conditions;
    }

    public void setConditions(List<Map<String, String>> conditions) {
        this.conditions = conditions;
    }
}
