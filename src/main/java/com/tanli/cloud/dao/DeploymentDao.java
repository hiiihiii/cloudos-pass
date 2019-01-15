package com.tanli.cloud.dao;

import com.tanli.cloud.model.Deployment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeploymentDao {
    public int addDeployment(Deployment deployment);
}
