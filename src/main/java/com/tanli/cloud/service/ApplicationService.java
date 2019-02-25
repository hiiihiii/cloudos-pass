package com.tanli.cloud.service;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public interface ApplicationService {
    public APIResponse getApplications(User user);
    public APIResponse getServiceInfo(User user, String deployid);
}
