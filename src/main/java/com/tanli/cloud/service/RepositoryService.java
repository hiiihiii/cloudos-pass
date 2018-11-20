package com.tanli.cloud.service;

import com.tanli.cloud.model.response.Repository;

import java.util.List;

/**
 * Created by tanli on 2018/11/20 0020.
 */
public interface RepositoryService {
    public List<Repository> getRepoByUserid(String userid);
}
