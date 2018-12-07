package com.tanli.cloud.service;

import com.tanli.cloud.dao.UserDao;
import com.tanli.cloud.model.response.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class UserManageServiceImp implements UserManageService {

    @Autowired
    private UserDao userDao;

    @Override
    public User loginVerify(User user) {
        return userDao.loginVefiry(user);
    }
}
