package com.tanli.cloud.dao;

import com.tanli.cloud.model.ImageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tanli on 2018/11/21 0021.
 */
@Mapper
public interface ImageInfoDao {
    public int addImageInfo(ImageInfo imageInfo);
}
