package com.tanli.cloud.service;

import com.tanli.cloud.dao.UserLogDao;
import com.tanli.cloud.model.UserLog;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/28 0028.
 */
@Service
public class UserLogServiceImp implements UserLogService {
    @Autowired
    private UserLogDao userLogDao;

    private static final Logger LOGGE = LoggerFactory.getLogger(UserLogServiceImp.class);

    @Override
    public APIResponse getLogs(User user) {
        List<UserLog> userLogList = new ArrayList<>();
        try {
            userLogList = userLogDao.getUserLogs();
            return APIResponse.success(userLogList);
        } catch (Exception e) {
            LOGGE.info("[UserLogServiceImp Info]: " + "获取用户日志失败");
            e.printStackTrace();
        }
        return APIResponse.fail("获取用户日志失败");
    }

    @Override
    public APIResponse deleteLogs(User user, String[] ids) {
        Map<String, Integer> result = new HashMap<>();
        int success = 0, fail = 0;
        for(int i = 0 ; i < ids.length; i++){
            try {
                //操作日志
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                UserLog userLog = new UserLog();
                userLog.setUuid(UuidUtil.getUUID());
                userLog.setUser_id(user.getUser_uuid());
                userLog.setUsername(user.getUserName());
                userLog.setResoureType("日志");
                userLog.setResourceId(ids[i]);
                userLog.setOperation("删除日志");
                userLog.setIsDeleted("0");
                userLog.setCreate_time(nowStr);
                userLogDao.addUserLog(userLog);
                //删除日志
                int count = userLogDao.deleteLogById(ids[i]);
                if(count > 0){
                    success += 1;
                } else {
                    fail += 1;
                    LOGGE.info("[UserLogServiceImp Info]: " + "删除日志" + ids[i] + "失败");
                }
            } catch (Exception e) {
                fail += 1;
                LOGGE.info("[UserLogServiceImp Info]: " + "删除日志" + ids[i] + "失败");
                e.printStackTrace();
            }
        }
        result.put("success", success);
        result.put("fail", fail);
        if(fail == ids.length) {
            return APIResponse.fail("删除日志失败");
        }
        return APIResponse.success(result);
    }
}
