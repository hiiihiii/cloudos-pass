package com.tanli.cloud.dao;

import com.tanli.cloud.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/10/16 0016.
 */
@Mapper
public interface LoginDao {
    public User loginVefiry(User user);
}
