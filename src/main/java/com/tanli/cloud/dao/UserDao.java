package com.tanli.cloud.dao;

import com.tanli.cloud.model.response.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanli on 2018/10/16 0016.
 */
@Mapper
public interface UserDao {
    public User loginVefiry(User user);
    public List<User> getAllUser();
    public int addUser(User user);
    public int deleteById(@Param("id")String userid);
}
