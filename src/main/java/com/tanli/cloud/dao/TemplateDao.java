package com.tanli.cloud.dao;

import com.tanli.cloud.model.Template;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tanli on 2018/12/6 0006.
 */
@Mapper
public interface TemplateDao {
    public int addTemplate(Template template);
}
