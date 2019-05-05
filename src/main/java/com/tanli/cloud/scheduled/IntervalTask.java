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

import java.util.ArrayList;
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
        System.out.println("updatePods 每隔2分钟" + new Date());
        List<K8s_Rc> dbk8s_rcs = k8sRcDao.getAllRc();
        List<K8s_Service> dbk8s_services = k8sServiceDao.getAllService();
        List<K8s_Pod> dbk8s_pods = k8sPodDao.getAllPods();
        //处理数据库中没有任何pod数据的情况
        if(dbk8s_pods.size() == 0 ) {
            for(int i = 0; i < dbk8s_rcs.size(); i++) {
                K8s_Rc rc = dbk8s_rcs.get(i);
                Map<String, String> selectors = JSONObject.fromObject(rc.getSelector());
                K8s_Service svc = dbk8s_services.stream()
                        .filter(k8s_service -> JSONObject.fromObject(k8s_service.getSelector()).equals(selectors))
                        .findFirst().orElse(null);
                List<Pod> pods = new ArrayList<>();
                pods = k8sClient.getPod(selectors);
                for(int j = pods.size()-1; j >= 0; j--) {
                    addPodToDB(rc, svc, pods.get(j));
                }
            }
            return;
        }
        //处理数据库中已有pod数据的情况
        for(int i = 0; i<dbk8s_pods.size(); i++) {
            K8s_Pod dbpod = dbk8s_pods.get(i);
            boolean isDeleted = true; //用于判断该pod是否已从k8s中删除
            Map<String, String> labels = JSONObject.fromObject(dbpod.getLabel());
            //根据labels判断与该pod相关的rc和svc
            K8s_Rc dbrc = dbk8s_rcs.stream()
                    .filter(rc -> {
                        if(JSONObject.fromObject(rc.getSelector()).equals(labels)){
                            return true;
                        } else {
                            return false;
                        }
                    }).findFirst().orElse(null);
            K8s_Service dbservice = dbk8s_services.stream()
                    .filter(k8s_service -> {
                        if(JSONObject.fromObject(k8s_service.getSelector()).equals(labels)){
                            return true;
                        } else {
                            return false;
                        }
                    }).findFirst().orElse(null);
            //从k8s中获取与labels相关的pod
            List<Pod> pods = new ArrayList<>();
            pods = k8sClient.getPod(labels);
            //复制数据，注意深、浅拷贝
            List<Pod> newPods = new ArrayList<>();
            for(int j = 0; j < pods.size(); j++) {
                newPods.add(pods.get(j));
            }
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            for(int j = 0; j < pods.size(); j++) {
                Pod pod = pods.get(j);
                if(dbpod.getName().equals(pod.getMetadata().getName())) {
                    isDeleted = false; // 表示该pod未被k8s删除，更新数据库
                    dbpod.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
                    dbpod.setStatus(pod.getStatus().getPhase());
                    dbpod.setUpdate_time(nowStr);
                    k8sPodDao.updatePod(dbpod);
                    newPods.remove(j);
                    System.out.println("tl_pod表【更新】：" + dbpod.getUuid());
                    break;
                }
            }
            if(isDeleted) { //该pod已被k8s删除，从数据库中删除该pod
                k8sPodDao.deletePodById(dbpod.getUuid());
                System.out.println("tl_pod表【删除】：" + dbpod.getUuid());
            }
            //temp中剩下的就是可能是k8s新产生的，需要添加到数据库中
            for(int j = 0; j < newPods.size(); j++) {
                Pod tempPod = newPods.get(j);
                K8s_Pod temp = k8sPodDao.getAllPods().stream()
                        .filter(temp1 -> temp1.getName().equals(tempPod.getMetadata().getName()))
                        .findFirst().orElse(null);
                if(temp == null) {//如果这个for循环放在再外一层，就不需要判断了
                    //Pod newK8sPod = newPods.get(j);
                    //addPodToDB(dbrc, dbservice, newK8sPod);
                    addPodToDB(dbrc, dbservice, tempPod);
                }
            }
        }
    }

    public int addPodToDB(K8s_Rc rc, K8s_Service svc, Pod pod) {
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        K8s_Pod newPod = new K8s_Pod();
        newPod.setUuid(UuidUtil.getUUID());
        newPod.setRc_uuid(rc.getUuid());
        newPod.setSvc_uuid(svc.getUuid());
        newPod.setName(pod.getMetadata().getName());
        newPod.setNamespace(pod.getMetadata().getNamespace());
        newPod.setLabel(JSONObject.fromObject(pod.getMetadata().getLabels()).toString());
        //pod是单容器的
        newPod.setImage(pod.getSpec().getContainers().get(0).getImage());
        newPod.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
        newPod.setPodIP(pod.getStatus().getPodIP());
        newPod.setHostIP(pod.getStatus().getHostIP());
        newPod.setStatus(pod.getStatus().getPhase());
        newPod.setUpdate_time(nowStr);
        System.out.println("tl_pod表【新增】：" + newPod.getUuid());
        return k8sPodDao.addPod(newPod);
    }
}
