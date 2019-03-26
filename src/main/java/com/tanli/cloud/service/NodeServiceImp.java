package com.tanli.cloud.service;

import com.tanli.cloud.model.response.K8sNode;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.NodeAddress;
import io.fabric8.kubernetes.api.model.NodeCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class NodeServiceImp implements NodeService {
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger LOGGE = LoggerFactory.getLogger(NodeServiceImp.class);

    @Override
    public APIResponse getNodes() {
        List<K8sNode> k8sNodes = new ArrayList<>();
        K8sClient k8sClient = new K8sClient();
        List<Node> nodes = k8sClient.getNode();
        nodes.stream().forEach(node -> {
            K8sNode k8sNode = new K8sNode();
            k8sNode.setName(node.getMetadata().getName());
            List<NodeCondition> nodeConditions = node.getStatus().getConditions();
            for(int i = 0; i < nodeConditions.size(); i++) {
                NodeCondition condition = nodeConditions.get(i);
                switch (condition.getType()) {
                    case "OutOfDisk":
                        k8sNode.setOutOfDisk(condition.getStatus());
                        break;
                    case "MemoryPressure":
                        k8sNode.setMemoryPressure(condition.getStatus());
                        break;
                    case "DiskPressure":
                        k8sNode.setDiskPressure(condition.getStatus());
                        break;
                    case "Ready":
                        k8sNode.setReady(condition.getStatus());
                        break;
                }
            }
            List<NodeAddress> addresses = node.getStatus().getAddresses();
            for(int i = 0; i < addresses.size(); i++) {
                NodeAddress address = addresses.get(i);
                switch (address.getType()) {
                    case "LegacyHostIP":
                        k8sNode.setIegacyHostIP(address.getAddress());
                        break;
                    case "InternalIP":
                        k8sNode.setInternalIP(address.getAddress());
                        break;
                }
            }
            k8sNodes.add(k8sNode);
        });
    return APIResponse.success(k8sNodes);
    }
}
