package com.tanli.cloud.model.response;

/**
 * Created by Administrator on 2019/3/26 0026.
 */
public class HarborProject {
    private int project_id;
    private int owner_id;
    private String name;
    private int deleted;
    private String owner_name;

//    @JsonProperty("public")
//    private int pub;

    private int repo_count;

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

//    public int getPub() {
//        return pub;
//    }
//
//    public void setPub(int pub) {
//        this.pub = pub;
//    }

    public int getRepo_count() {
        return repo_count;
    }

    public void setRepo_count(int repo_count) {
        this.repo_count = repo_count;
    }

    public String toString(){
        return "{  project_id: " + this.getProject_id() +
                ", owner_id: " + this.getOwner_id() +
                ", owner_name: "+ this.getOwner_name() +
                ", name: " + this.getName() +
                ", project_id: " + this.getProject_id() +
                "}";
    }
}
