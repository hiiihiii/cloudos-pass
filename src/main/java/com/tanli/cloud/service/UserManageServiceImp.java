package com.tanli.cloud.service;

import com.tanli.cloud.constant.EnvConst;
import com.tanli.cloud.constant.SystemConst;
import com.tanli.cloud.dao.*;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.Project;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.UserLog;
import com.tanli.cloud.model.response.HarborProject;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@Service
public class UserManageServiceImp implements UserManageService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLogDao userLogDao;
    @Autowired
    private RepositoryDao repositoryDao;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private K8sClient k8sClient;

    private static final org.slf4j.Logger LOGGE = org.slf4j.LoggerFactory.getLogger(UserManageServiceImp.class);

    @Override
    public User loginVerify(User user) {
        //操作日志
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        UserLog userLog = new UserLog(UuidUtil.getUUID(),user.getUser_uuid(),
                user.getUserName(),"User", user.getUser_uuid(), "登录", "0", nowStr);

        User login_user = userDao.loginVefiry(user);
        userLog.setUser_id(login_user.getUser_uuid());
        userLog.setResourceId(login_user.getUser_uuid());
        userLogDao.addUserLog(userLog);
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
        repository.setUrl("http://"+ EnvConst.harbor_api_ip);
        repository.setLogin_name(EnvConst.harbor_username);
        repository.setLogin_psd(EnvConst.harbor_password);
        repository.setProject_name(repository.getRepo_name());
        repository.setCreate_time(nowStr);
        repository.setUpdate_time(nowStr);
        k8sClient.createNamespace("test");
        //调用Harbor API创建project
        Boolean result = addHarborProject(repository.getRepo_name());
        if(result) {
            try {
//                k8sClient.createNamespace(user.getUserName());
                int count = userDao.addUser(user);
                repositoryDao.addRepo(repository);
                //操作日志
                UserLog userLog = new UserLog();
                userLog.setUuid(UuidUtil.getUUID());
                userLog.setUser_id(user.getUser_uuid());
                userLog.setUsername(user.getUserName());
                userLog.setResoureType("User");
                userLog.setResourceId(user.getUser_uuid());
                userLog.setOperation("增加用户");
                userLog.setIsDeleted("0");
                userLog.setCreate_time(nowStr);
                userLogDao.addUserLog(userLog);
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
    public APIResponse editUser(User user, User editUser) {
        User dbUser = userDao.getAllUser().stream().filter(user1 -> user1.getUser_uuid().equals(editUser.getUser_uuid())).findFirst().orElse(null);
        if(dbUser == null) {
            return APIResponse.fail("用户"+editUser.getUserName()+"不存在，修改");
        }
        try {
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
//            dbUser.setPassword(editUser.getPassword());
            dbUser.setEmail(editUser.getEmail());
            dbUser.setTelephone(editUser.getTelephone());
            dbUser.setUpdate_time(nowStr);
            UserLog userLog = new UserLog(UuidUtil.getUUID(),
                    user.getUser_uuid(),
                    user.getUserName(),
                    SystemConst.USER,
                    editUser.getUser_uuid(),
                    "修改用户"+editUser.getUserName()+"的基本信息", "0", nowStr);
            userDao.updateUser(dbUser);
            return APIResponse.success("修改"+editUser.getUserName()+"的基本信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail("修改"+editUser.getUserName()+"的基本信息失败");
        }
    }

    @Override
    public APIResponse deleteByIds(User user, String[] ids) {
        Map<String, Integer> result = new HashMap<>();
        List<ImageInfo> imageInfos = imageInfoDao.getAllImages();
        List<Template> templateList = templateDao.getAllTemplate();

        int success = 0, fail = 0;
        for(int i = 0 ; i < ids.length; i++){
            //判断用户是否拥有镜像、模板
            String id = ids[i];
            List<Repository> repositories = repositoryDao.getRepoByUserid(id);
            ImageInfo tempImage = imageInfos.stream()
                    .filter(imageInfo -> imageInfo.getUser_id().equals(id))
                    .findFirst().orElse(null);
            Template template = templateList.stream()
                    .filter(template1 -> template1.getUuid().equals(id))
                    .findFirst().orElse(null);
            User deleteUser = userDao.getAllUser().stream().filter(user1 -> user1.getUser_uuid().equals(id)).findFirst().orElse(null);
            if(tempImage!=null || template!=null) {//用户id下有应用不能删除
                fail += 1;
                break;
            }
            try {
                //删除数据库中数据
                repositoryDao.deleteRepo(id);
                //删除Harbor中的项目
                repositories.stream().forEach(repository -> {
                    deleteHarborProject(repository.getRepo_name());
                });
                //删除k8s中的命名空间
//                k8sClient.deleteNamespace(deleteUser.getUserName());
                //删除用户
                int count = userDao.deleteById(ids[i]);
                //操作日志
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                UserLog userLog = new UserLog(UuidUtil.getUUID(),
                        user.getUser_uuid(), user.getUserName(), "User", ids[i],
                        "删除用户",
                        "0",
                        nowStr);
                userLogDao.addUserLog(userLog);
                if(count > 0){
                    success += 1;
                } else {
                    fail += 1;
                    LOGGE.info("[UserManageServiceImp Info]: " + "删除用户" + ids[i] + "失败");
                }
            } catch (Exception e) {
                fail += 1;
                LOGGE.info("[UserManageServiceImp Info]: " + "删除用户" + ids[i] + "失败");
                e.printStackTrace();
            }
        }
        result.put("success", success);
        result.put("fail", fail);
        if(fail == ids.length) {
            return APIResponse.fail("删除用户失败");
        }
        return APIResponse.success(result);
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
        String url = EnvConst.harbor_api_prefix + "projects";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(EnvConst.harbor_username, EnvConst.harbor_password));
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
                String url = EnvConst.harbor_api_prefix + "projects/" + harborProject.getProject_id();
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
        String url = EnvConst.harbor_api_prefix + "projects?project_name=" + project_name;
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(EnvConst.harbor_username, EnvConst.harbor_password));
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
