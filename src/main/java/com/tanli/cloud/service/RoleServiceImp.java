package com.tanli.cloud.service;

import com.tanli.cloud.dao.RoleDao;
import com.tanli.cloud.model.Role;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    public RoleDao roleDao;

    private static final org.slf4j.Logger LOGGE = LoggerFactory.getLogger(RoleServiceImp.class);

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public APIResponse getRoles(User user) {
        try {
            List<Role> roles = roleDao.getAllRole();
            return APIResponse.success(roles);
        } catch (Exception e){
            LOGGE.info("[RoleServiceImp Info]: " + "获取角色信息失败");
            return APIResponse.fail("获取角色信息失败");
        }
    }
}
