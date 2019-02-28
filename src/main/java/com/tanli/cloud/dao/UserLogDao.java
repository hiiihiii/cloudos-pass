package com.tanli.cloud.dao;

import com.tanli.cloud.model.UserLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/2/28 0028.
 */
@Mapper
public interface UserLogDao {
    public int addUserLog(UserLog userLog);
    public List<UserDao> getUserLogs();
}
