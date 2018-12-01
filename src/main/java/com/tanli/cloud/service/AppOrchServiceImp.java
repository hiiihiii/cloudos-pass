package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.RepositoryDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanli on 2018/12/1 0001.
 */
@Service
public class AppOrchServiceImp implements AppOrchService{
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private RepositoryDao repositoryDao;

    private static final Logger LOGGE = LoggerFactory.getLogger(AppOrchServiceImp.class);

    @Override
    public APIResponse getImageInfo(LoginingUser user) {
        String userid = user.getUser_uuid();
        List<Repository> repositories = repositoryDao.getRepoByUserid(userid);
        List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
        repositories.forEach(repository -> {
            List<ImageInfo> temp = imageInfoDao.getImages(repository.getRepo_uuid(), userid);
            temp.forEach(imageInfo -> {
                imageInfo.setType(repository.getRepo_type());
            });
            imageInfos.addAll(temp);
        });
        return APIResponse.success(imageInfos);
    }
}
