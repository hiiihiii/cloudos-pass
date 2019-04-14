package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Pod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanli on 2019/2/13 0013.
 */
@Mapper
public interface K8sPodDao {
    public int addPod(K8s_Pod pod);
    public List<K8s_Pod> getAllPods();
    public int updatePod(K8s_Pod pod);
    public int deletePodById(@Param("pod_uuid") String id);
}
