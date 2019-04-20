package com.tanli.cloud.service;

import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tanli on 2018/12/1 0001.
 */
public interface AppOrchService {
    public APIResponse getImageInfo(User user);

    public APIResponse addTemplate(User user, Template template, MultipartFile file);

    public APIResponse editTemplate(User user, String uuid, String description, MultipartFile file);
}
