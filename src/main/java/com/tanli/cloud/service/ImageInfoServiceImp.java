package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.RepositoryDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tanli on 2018/11/21 0021.
 */
@Service
public class ImageInfoServiceImp implements ImageInfoService {
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private RepositoryDao repositoryDao;

    @Override
    public int addImageInfo(ImageInfo imageInfo) {
        return imageInfoDao.addImageInfo(imageInfo);
    }

    @Override
    public APIResponse getImages(LoginingUser user, String repoType, String appType) {
        String userId = user.getUser_uuid();
        List<ImageInfo> imageInfos = new ArrayList<>();
        //获取用户仓库
        List<Repository> repositories = repositoryDao.getRepoByUserid(userId)
                .stream()
                .filter(repository -> repository.getRepo_type().equals(repoType))
                .collect(Collectors.toList());
        repositories.forEach(repository -> {
            List<ImageInfo> temps = imageInfoDao.getImages(repository.getRepo_uuid(), userId);
            temps.forEach(imageInfo -> imageInfo.setType(repoType));
            imageInfos.addAll(temps);
        });
        return APIResponse.success(imageInfos);
    }

    @Override
    public List<ImageInfo> getImagesAll() {
        return null;
    }

}
