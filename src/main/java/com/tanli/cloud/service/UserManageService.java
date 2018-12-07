package com.tanli.cloud.service;

import com.tanli.cloud.model.response.User;

/**
 * Created by tanli on 2018/10/15 0015.
 */
public interface UserManageService {
    public User loginVerify(User user);
}
