package com.tanli.cloud.service;

import com.tanli.cloud.model.Role;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by tanli on 2018/10/15 0015.
 */
public interface RoleService {

    public void addRole(Role role);

    public APIResponse getRoles(LoginingUser user);
}
