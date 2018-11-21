package com.tanli.cloud.service;

import com.tanli.cloud.dao.RepositoryDao;
import com.tanli.cloud.model.response.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanli on 2018/11/20 0020.
 */
@Service
public class RepositoryServiceImp implements RepositoryService{
    @Autowired
    private RepositoryDao repositoryDao;

    @Override
    public List<Repository> getRepoByUserid(String userid) {
        return repositoryDao.getRepoByUserid(userid);
    }
}
