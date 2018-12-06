package com.tanli.cloud.service;

import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tanli on 2018/12/1 0001.
 */
public interface AppOrchService {
    public APIResponse getImageInfo(LoginingUser user);

    public APIResponse addTemplate(LoginingUser user, Template template, MultipartFile file);
}
