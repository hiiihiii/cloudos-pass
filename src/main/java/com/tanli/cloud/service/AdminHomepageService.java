package com.tanli.cloud.service;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by Administrator on 2019/3/7 0007.
 */
public interface AdminHomepageService {
    public APIResponse getAllImages(User user);
    public APIResponse getAllTemplates(User user);
}
