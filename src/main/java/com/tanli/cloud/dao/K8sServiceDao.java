package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Service;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface K8sServiceDao {
    public int addService(K8s_Service k8s_service);
    public List<K8s_Service> getAllService();
}
