package com.tanli.cloud.dao;

import com.tanli.cloud.model.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Mapper
public interface RoleDao {
    int addRole(Role role);
}
