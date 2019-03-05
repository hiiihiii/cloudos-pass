package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Rc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface K8sRcDao {
    public int addRc(K8s_Rc k8s_rc);
    public List<K8s_Rc> getAllRc();
    public int updateReplicas(@Param("id") String id, @Param("replicas") String replicas);
}
