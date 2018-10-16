package com.tanli.cloud.service;

import com.tanli.cloud.model.User;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by tanli on 2018/10/15 0015.
 */
public interface UserManageService {
    public APIResponse loginVerify(User user);
}
