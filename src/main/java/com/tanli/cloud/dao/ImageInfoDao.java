package com.tanli.cloud.dao;

import com.tanli.cloud.model.ImageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanli on 2018/11/21 0021.
 */
@Mapper
public interface ImageInfoDao {
    public int addImageInfo(ImageInfo imageInfo);
    public int updateImageInfo(ImageInfo imageInfo);
    public List<ImageInfo> getImages(@Param("repoid") String repoid, @Param("userid") String userid);
    public List<ImageInfo> getImagesAll(@Param("userid") String userid);
    public int deleteImageById(@Param("imageid") String imageid);
    public int updateDeployCount(@Param("imageid") String imageid, @Param("count")int count);
    public List<ImageInfo> getAllImages();
}
