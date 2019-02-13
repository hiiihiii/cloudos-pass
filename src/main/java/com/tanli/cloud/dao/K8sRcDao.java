package com.tanli.cloud.dao;

import com.tanli.cloud.model.K8s_Rc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface K8sRcDao {
    public int addRc(K8s_Rc k8s_rc);
}
