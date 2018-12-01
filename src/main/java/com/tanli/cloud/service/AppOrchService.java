package com.tanli.cloud.service;

import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;

/**
 * Created by tanli on 2018/12/1 0001.
 */
public interface AppOrchService {
    public APIResponse getImageInfo(LoginingUser user);
}
