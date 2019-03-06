package com.tanli.cloud.model;

public class K8s_Rc {
    private String uuid;
    private String deployment_uuid;
    private String name;
    private String namespace;
    private String replicas;// 实时的实例个数，是可变的
    private String desiredCount;// 期望的实例个数，不变
    private String selector;
    private String template;
    private String content;
    private String scaleType;// 伸缩方式，auto代表自动，manual代表手动
    private String create_time;
    private String update_time;

    public String getScaleType() {
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    public String getDesiredCount() {
        return desiredCount;
    }

    public void setDesiredCount(String desiredCount) {
        this.desiredCount = desiredCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeployment_uuid() {
        return deployment_uuid;
    }

    public void setDeployment_uuid(String deployment_uuid) {
        this.deployment_uuid = deployment_uuid;
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

    public String getReplicas() {
        return replicas;
    }

    public void setReplicas(String relicas) {
        this.replicas = relicas;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
