package com.tanli.cloud.service;

import com.tanli.cloud.dao.RoleDao;
import com.tanli.cloud.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    public RoleDao roleDao;

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}
