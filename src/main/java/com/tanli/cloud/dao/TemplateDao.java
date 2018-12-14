package com.tanli.cloud.dao;

import com.tanli.cloud.model.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanli on 2018/12/6 0006.
 */
@Mapper
public interface TemplateDao {
    public int addTemplate(Template template);
    public List<Template> getAllTemplate();
    public int publishTemplate(@Param("templateId")String templateId);
    public int deleteById(@Param("templateId")String templateId);
}
