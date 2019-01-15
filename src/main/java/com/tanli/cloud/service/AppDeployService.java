package com.tanli.cloud.service;

import com.tanli.cloud.model.DeployedImage;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;

public interface AppDeployService {

    public APIResponse deployImage(User user, DeployedImage deployedImage);
}
