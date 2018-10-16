package com.tanli.cloud.model;

import java.sql.Timestamp;

/**
 * Created by tanli on 2018/10/16 0016.
 */
public class User {

    private String user_uuid;

    private String role_uuid;

    private String userName;

    private String password;
    private String email;
    private String telephone;
    private Timestamp create_time;
    private Timestamp update_time;

    public String getUserId() {
        return user_uuid;
    }

    public void setUserId(String userId) {
        this.user_uuid = userId;
    }

    public String getRoleId() {
        return role_uuid;
    }

    public void setRoleId(String roleId) {
        this.role_uuid = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
