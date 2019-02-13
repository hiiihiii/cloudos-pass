package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Service;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface K8sServiceDao {
    public int addService(K8s_Service k8s_service);
}
