package com.tanli.cloud.service;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by Administrator on 2019/2/28 0028.
 */
public interface UserLogService {
    public APIResponse getLogs(User user);
    public APIResponse deleteLogs(User user,String[] ids);
}
