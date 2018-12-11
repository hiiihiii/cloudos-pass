package com.tanli.cloud.service;

import com.tanli.cloud.dao.UserDao;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class UserManageServiceImp implements UserManageService {

    @Autowired
    private UserDao userDao;

    private static final org.slf4j.Logger LOGGE = org.slf4j.LoggerFactory.getLogger(UserManageServiceImp.class);

    @Override
    public User loginVerify(User user) {
        return userDao.loginVefiry(user);
    }

    @Override
    public APIResponse getUsers(User user) {
        try {
            List<User> users = userDao.getAllUser();
            return APIResponse.success(users);
        } catch (Exception e) {
            LOGGE.info("[UserManageServiceImp Info]: " + "获取用户数据失败");
            return APIResponse.fail("获取用户数据失败");
        }
    }

    @Override
    public APIResponse addUser(User user) {
        user.setUser_uuid(UuidUtil.getUUID());
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        user.setCreate_time(nowStr);
        user.setUpdate_time(nowStr);
        try {
            int count = userDao.addUser(user);
            if(count > 0){
                return APIResponse.success();
            } else {
                LOGGE.info("[UserManageServiceImp Info]: " + "增加用户失败");
            }
        } catch (Exception e) {
            LOGGE.info("[UserManageServiceImp Info]: " + "增加用户失败");
            e.printStackTrace();
        }
        return APIResponse.fail("增加用户失败");
    }
}
