package com.tanli.cloud.scheduled;

import com.tanli.cloud.dao.K8sPodDao;
import com.tanli.cloud.dao.K8sRcDao;
import com.tanli.cloud.dao.K8sServiceDao;
import com.tanli.cloud.model.K8s_Pod;
import com.tanli.cloud.model.K8s_Rc;
import com.tanli.cloud.model.K8s_Service;
import com.tanli.cloud.utils.K8sClient;
import com.tanli.cloud.utils.UuidUtil;
import io.fabric8.kubernetes.api.model.Pod;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/16 0016.
 */
@Component
public class IntervalTask {
    @Autowired
    private K8sRcDao k8sRcDao;
    @Autowired
    private K8sServiceDao k8sServiceDao;
    @Autowired
    private K8sClient k8sClient;
    @Autowired
    private K8sPodDao k8sPodDao;
    //表示方法执行完成后2分钟
    //根据RC更新Pod
    @Transactional
    @Scheduled(fixedDelay=120000)
    public void updatePods() throws InterruptedException {
        List<K8s_Rc> rcs = k8sRcDao.getAllRc();
        List<K8s_Pod> k8s_pods = k8sPodDao.getAllPods();
        List<K8s_Service> k8s_services = k8sServiceDao.getAllService();
        for(int i = 0; i < rcs.size(); i++) {
            K8s_Rc rc = rcs.get(i);
            Map<String, String> selector = JSONObject.fromObject(rc.getSelector());
            //获取与RC相关的SVC
            K8s_Service svc = k8s_services.stream()
                    .filter(k8s_service -> {
                        if(JSONObject.fromObject(k8s_service.getSelector()).equals(selector)){
                            return true;
                        } else {
                            return false;
                        }
                    }).findFirst().orElse(null);
            //从Kubernetes中获取rc相关的pod
            List<Pod> pods = k8sClient.getPod(selector);
            for(int j = 0; j<pods.size();j++) {
                Pod pod = pods.get(j);
                //判断该pod是否已存在数据库中
                K8s_Pod k8s_pod = k8s_pods.stream()
                        .filter(tempPod->{
                            if(tempPod.getName().equals(pod.getMetadata().getName()) &&
                                    tempPod.getNamespace().equals(pod.getMetadata().getNamespace())) {
                                return true;
                            } else {
                                return false;
                            }
                        }).findFirst().orElse(null);
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                if(k8s_pod!=null) { //已在数据库中存在了
                    k8s_pod.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
                    k8s_pod.setStatus(pod.getStatus().getPhase());
                    k8s_pod.setUpdate_time(nowStr);
                    System.out.println(k8s_pod.getUuid());
                    k8sPodDao.updatePod(k8s_pod);
                } else { //不存在数据库中
                    k8s_pod = new K8s_Pod();
                    k8s_pod.setUuid(UuidUtil.getUUID());
                    k8s_pod.setRc_uuid(rc.getUuid());
                    k8s_pod.setSvc_uuid(svc.getUuid());
                    k8s_pod.setName(pod.getMetadata().getName());
                    k8s_pod.setNamespace(pod.getMetadata().getNamespace());
                    //pod是单容器的
                    k8s_pod.setImage(pod.getSpec().getContainers().get(0).getImage());
                    k8s_pod.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
                    k8s_pod.setPodIP(pod.getStatus().getPodIP());
                    k8s_pod.setHostIP(pod.getStatus().getHostIP());
                    k8s_pod.setStatus(pod.getStatus().getPhase());
                    k8s_pod.setUpdate_time(nowStr);
                    System.out.println(k8s_pod.getUuid());
                    k8sPodDao.addPod(k8s_pod);
                }
            }
        }

        System.out.println("updatePods 每隔2分钟"+new Date());
    }
}
