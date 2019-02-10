package com.tanli.cloud.utils;

import com.tanli.cloud.model.DeployContainer;
import com.tanli.cloud.model.DeployedImage;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanli on 2019/2/9 0009.
 */
public class K8sClient {

    private static final String k8s_api_address = "132.232.99.189:8080";

    public K8sClient() {}

    /**
     * 构建Kubernetes Client
     * @return
     */
    public KubernetesClient buildClient(){
        Config config = new ConfigBuilder().withMasterUrl(k8s_api_address).build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        return client;
    }

    /**
     * 测试创建namespace
     */
    public void testCreateNamespace() {
        KubernetesClient kube = buildClient();
        Namespace ns = new Namespace();
        ns.setApiVersion("v1");
        ns.setKind("Namespace");
        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setName("ns-namespace");
        ns.setMetadata(objectMeta);
        kube.namespaces().create(ns);
    }

    /**
     * 创建service
     * @param deployedImage
     */
    public void createService(DeployedImage deployedImage) {
        KubernetesClient _kube = buildClient();

        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);
        Service service = new Service();

        service.setApiVersion("v1");
        service.setKind("Service");

        ObjectMeta svcMeta = new ObjectMeta();
        svcMeta.setName(deployContainer.getImageName() + "-svc");
        svcMeta.setNamespace("default");
        service.setMetadata(svcMeta);

        ServiceSpec svcSpec = new ServiceSpec();
        List<ServicePort> servicePorts = new ArrayList<>();
        for(int i = 0; i < deployContainer.getPorts().length; i++) {
            ServicePort port = new ServicePort();
            Map<String, Object> temp = deployContainer.getPorts()[i];
            port.setName((String) temp.get("name"));
            port.setNodePort((int) temp.get("nodePort"));
            port.setPort((int) temp.get("port"));
            port.setTargetPort((IntOrString) temp.get("targetPort"));
            port.setProtocol((String) temp.get("protocol"));
            servicePorts.add(port);
        }
        svcSpec.setPorts(servicePorts);
        Map<String, String> selectors = new HashMap<>();
        selectors.put("app", deployContainer.getImageName());
        svcSpec.setSelector(selectors);
        svcSpec.setType("NodePort");
        service.setSpec(svcSpec);

        _kube.services().create(service);
    }

    /**
     * 创建RC
     * @param deployedImage
     */
    public void createRC(DeployedImage deployedImage) {
        KubernetesClient _kube = buildClient();

        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);

        List<Container> containerList = new ArrayList<Container>();
        Container container = new Container();
        container.setName(deployContainer.getImageName());
        List<String> tempargs = new ArrayList<>();
        for(int i = 0; i < deployContainer.getArgs().length; i++) {
            tempargs.add(deployContainer.getArgs()[i]);
        }
        container.setArgs(tempargs);
        List<String> tempcmd = new ArrayList<>();
        for(int i = 0; i < deployContainer.getArgs().length; i++) {
            tempcmd.add(deployContainer.getArgs()[i]);
        }
        container.setCommand(tempcmd);
        List<EnvVar> tempEnv = new ArrayList<>();
        for(int i = 0; i < deployContainer.getEnv().length; i++) {
            EnvVar temp = new EnvVar();
            temp.setName(deployContainer.getEnv()[i].get("name"));
            temp.setValue(deployContainer.getEnv()[i].get("value"));
            tempEnv.add(temp);
        }
        container.setEnv(tempEnv);
        container.setImage(deployContainer.getImage_source_url());
        container.setImagePullPolicy("IfNotPresent");
        List<ContainerPort> tempPorts = new ArrayList<>();
        for(int i = 0; i < deployContainer.getPorts().length; i++) {
            ContainerPort port = new ContainerPort();
            port.setContainerPort((int) deployContainer.getPorts()[i].get("targetPort"));
            port.setName((String) deployContainer.getPorts()[i].get("name"));
            tempPorts.add(port);
        }
        container.setPorts(tempPorts);
        containerList.add(container);

        ReplicationController rc = new ReplicationController();
        //Map<String, String> rcLabels = new HashMap<String, String>();
        Map<String, String> selectors = new HashMap<String, String>();
        selectors.put("app", deployContainer.getImageName());

        rc.setKind("ReplicationController");
        rc.setApiVersion("v1");

        ObjectMeta rcMeta = new ObjectMeta();
        rcMeta.setName(deployContainer.getImageName() + "-rc");
        //rcMeta.setLabels(rcLabels);
        rcMeta.setNamespace("default");
        rc.setMetadata(rcMeta);

        ReplicationControllerSpec rcSpec = new ReplicationControllerSpec();
        rcSpec.setReplicas(deployContainer.getReplicas());
        rcSpec.setSelector(selectors);
        PodTemplateSpec podTemplateSpec = new PodTemplateSpec();
            ObjectMeta podMeta = new ObjectMeta();
            podMeta.setNamespace("default");
            podMeta.setLabels(selectors);//label和rc中的selector一致
        podTemplateSpec.setMetadata(podMeta);
            PodSpec podSpec = new PodSpec();
            podSpec.setContainers(containerList);
            podSpec.setRestartPolicy("Always");
        podTemplateSpec.setSpec(podSpec);
        rcSpec.setTemplate(podTemplateSpec);
        rc.setSpec(rcSpec);

        _kube.replicationControllers().create(rc);
    }
}
