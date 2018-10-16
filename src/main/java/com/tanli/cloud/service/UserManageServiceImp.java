package com.tanli.cloud.service;

import com.tanli.cloud.dao.LoginDao;
import com.tanli.cloud.model.User;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class UserManageServiceImp implements UserManageService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public APIResponse loginVerify(User user) {
        User test = loginDao.loginVefiry(user);
        if(null != test){
            return APIResponse.success(test);
        } else {
            return APIResponse.fail("fail");
        }
    }
}
