package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.RepositoryDao;
import com.tanli.cloud.dao.UserDao;
import com.tanli.cloud.model.Project;
import com.tanli.cloud.model.response.HarborProject;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class UserManageServiceImp implements UserManageService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RepositoryDao repositoryDao;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private ImageInfoDao imageInfoDao;

    private String harbor_api_ip = "";
    private String harbor_username = "";
    private String harbor_password = "";
    private String harbor_api_prefix = "";

    private static final org.slf4j.Logger LOGGE = org.slf4j.LoggerFactory.getLogger(UserManageServiceImp.class);

    @Override
    public User loginVerify(User user) {
        User login_user = userDao.loginVefiry(user);
        return login_user;
    }

    @Override
    public APIResponse getUsers(User user) {
        try {
            List<User> users = userDao.getAllUser();
            return APIResponse.success(users);
        } catch (Exception e) {
            LOGGE.info("[UserManageServiceImp Info]: " + "获取用户数据失败");
            return APIResponse.fail("获取用户数据失败");
        }
    }

    @Override
    public APIResponse addUser(User user) {
        user.setUser_uuid(UuidUtil.getUUID());
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        user.setCreate_time(nowStr);
        user.setUpdate_time(nowStr);
        Repository repository =new Repository();
        repository.setRepo_uuid(UuidUtil.getUUID());
        repository.setUser_uuid(user.getUser_uuid());
        repository.setRepo_name(user.getUserName()+"_project");
        repository.setRepo_type("private");
        repository.setUrl("http://"+ harbor_api_ip);
        repository.setLogin_name(harbor_username);
        repository.setLogin_psd(harbor_password);
        repository.setProject_name(repository.getRepo_name());
        repository.setCreate_time(nowStr);
        repository.setUpdate_time(nowStr);

        //调用Harbor API创建project
        Boolean result = addHarborProject(repository.getRepo_name());
        if(result) {
            try {
                int count = userDao.addUser(user);
                repositoryDao.addRepo(repository);
                if(count > 0){
                    return APIResponse.success();
                } else {
                    LOGGE.info("[UserManageServiceImp Info]: " + "增加用户失败");
                }
            } catch (Exception e) {
                LOGGE.info("[UserManageServiceImp Info]: " + "增加用户失败");
                e.printStackTrace();
            }
        }
        return APIResponse.fail("增加用户失败");
    }

    @Override
    public APIResponse deleteByIds(User user, String[] ids) {
        return null;
    }

    @Override
    public APIResponse checkUserName(String username) {
        try {
            User user = userDao.getAllUser().stream()
                    .filter(user1 -> user1.getUserName().equals(username))
                    .findFirst().orElse(null);
            if(user == null) {
                return APIResponse.success(true);
            } else {
                return APIResponse.success(false);
            }
        } catch (Exception e) {
            LOGGE.info("[UserManageServiceImp Info]: " + "验证用户名" + username + "失败");
            e.printStackTrace();
        }
        return APIResponse.fail(false);
    }

    /**
     * 使用restTemplate调用harbor API创建project
     * @param projectName
     * @return
     */
    private Boolean addHarborProject(String projectName) {
        Boolean result = false;
        String url = harbor_api_prefix + "projects";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(harbor_username, harbor_password));
            Project project = new Project(projectName, 0);
            HttpEntity<Project> request = new HttpEntity<>(project);
            LOGGE.info("[UserManageServiceImp Info]: " + "POST "+url);
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            ResponseEntity<String> response = restTemplate.postForEntity( url, request, String.class);
            String code = response.getStatusCode().toString();
            if(code.equals("201")) {
                return true;
            }
        } catch (Exception e) {
            LOGGE.info("[UserManageServiceImp Info]: " + "向Harbor中添加project失败");
            e.printStackTrace();
        }
        return result;
    }

    private Boolean deleteHarborProject(String project_name){
        Boolean result = false;
        HarborProject harborProject = getHarborProjects(project_name);
        if(harborProject != null) {
            try {
                String url = harbor_api_prefix + "projects/" + harborProject.getProject_id();
                HttpEntity<String> httpEntity = new HttpEntity<String>("");
                LOGGE.info("[UserManageServiceImp Info]: " + "DELETE " + url);
                ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
                if(responseEntity.getStatusCode().toString().equals("200")) {
                    return true;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询harbor项目
     * @param project_name
     * @return
     */
    private HarborProject getHarborProjects(String project_name){
        HarborProject[] harborProjects = new HarborProject[0];
        String url = harbor_api_prefix + "projects?project_name=" + project_name;
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(harbor_username, harbor_password));
        try {
            LOGGE.info("[UserManageServiceImp Info]: " + "GET " + url);
            harborProjects = restTemplate.getForObject(url, HarborProject[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(harborProjects.length > 0) {
            System.out.print(harborProjects[0].toString());
            return harborProjects[0];
        } else {
            return null;
        }
    }
}
