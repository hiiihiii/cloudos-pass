package com.tanli.cloud.dao;

import com.tanli.cloud.model.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/2/28 0028.
 */
@Mapper
public interface UserLogDao {
    public int addUserLog(UserLog userLog);
    public List<UserLog> getUserLogs();
    public int deleteLogById(@Param("id")String id);
}
