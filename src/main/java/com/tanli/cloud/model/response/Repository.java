package com.tanli.cloud.model.response;

/**
 * Created by tanli on 2018/11/20 0020.
 */
public class Repository {
    private String repo_uuid;
    private String user_uuid;
    private String repo_name;
    private String repo_type;
    private String login_name;
    private String login_psd;
    private String project_name;
    private String url;
    private String create_time;
    private String update_time;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_psd() {
        return login_psd;
    }

    public void setLogin_psd(String login_psd) {
        this.login_psd = login_psd;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Repository(String repo_uuid, String user_uuid, String repo_name, String repo_type, String url, String create_time, String update_time,
                      String login_name, String login_psd, String project_name) {
        this.repo_uuid = repo_uuid;
        this.user_uuid = user_uuid;
        this.repo_name = repo_name;
        this.repo_type = repo_type;
        this.url = url;
        this.create_time = create_time;
        this.update_time = update_time;
        this.login_name = login_name;
        this.login_psd = login_psd;
        this.project_name = project_name;
    }
    public Repository(){}

    public String getRepo_uuid() {
        return repo_uuid;
    }

    public void setRepo_uuid(String repo_uuid) {
        this.repo_uuid = repo_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_type() {
        return repo_type;
    }

    public void setRepo_type(String repo_type) {
        this.repo_type = repo_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
