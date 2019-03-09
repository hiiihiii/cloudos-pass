package com.tanli.cloud.dao;

import com.tanli.cloud.model.Deployment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeploymentDao {
    public int addDeployment(Deployment deployment);
    public List<Deployment> getDeployments(@Param("id")String userid);
    public List<Deployment> getAllDeployments();
}
