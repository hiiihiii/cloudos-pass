package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Pod;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tanli on 2019/2/13 0013.
 */
@Mapper
public interface K8sPodDao {
    public int addPod(K8s_Pod pod);
}
