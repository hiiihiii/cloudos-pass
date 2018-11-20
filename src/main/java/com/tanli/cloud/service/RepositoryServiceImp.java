package com.tanli.cloud.service;

import com.tanli.cloud.model.response.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanli on 2018/11/20 0020.
 */
@Service
public class RepositoryServiceImp implements RepositoryService{


    @Override
    public List<Repository> getRepoByUserid(String userid) {
        return null;
    }
}
