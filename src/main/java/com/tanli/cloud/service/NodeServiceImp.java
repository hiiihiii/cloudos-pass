package com.tanli.cloud.service;

import com.tanli.cloud.constant.EnvConst;
import com.tanli.cloud.model.K8s_Node;
import com.tanli.cloud.model.K8s_Node_Metadata;
import com.tanli.cloud.model.K8s_Node_Status;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.PropertyStrategyWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        String nodeUrl = EnvConst.k8s_api_prefix + "nodes";
        ResponseEntity<String> temp = restTemplate.getForEntity(nodeUrl, String.class);
        String jsonStr = temp.getBody();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray array = jsonObject.getJSONArray("items");
        List<K8s_Node> nodes = new ArrayList<>();
        array.forEach(one -> {
            JsonConfig cfg = new JsonConfig();
            cfg.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));

            cfg.setRootClass(K8s_Node_Metadata.class);
            K8s_Node_Metadata tem1 = (K8s_Node_Metadata) JSONObject.toBean(JSONObject.fromObject(one).getJSONObject("metadata"), cfg);

            cfg.setRootClass(K8s_Node_Status.class);
            K8s_Node_Status tem2 = (K8s_Node_Status) JSONObject.toBean(JSONObject.fromObject(one).getJSONObject("status"), cfg);

            nodes.add(new K8s_Node(tem1, tem2));
        });
        return APIResponse.success(nodes);
    }
}
