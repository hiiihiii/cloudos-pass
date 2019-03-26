package com.tanli.cloud.dao;

import com.tanli.cloud.model.response.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanli on 2018/11/20 0020.
 */
@Mapper
public interface RepositoryDao {
    public List<Repository> getRepoByUserid(String userid);
    public int addRepo(Repository repository);
    public int deleteRepo(@Param("userid")String userid);
}
