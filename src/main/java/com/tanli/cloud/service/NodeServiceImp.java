package com.tanli.cloud.service;

import com.tanli.cloud.constant.EnvConst;
import com.tanli.cloud.model.K8s_Node;
import com.tanli.cloud.model.K8s_Node_Metadata;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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

    @Override
    public void getNodes() {
        String nodeUrl = EnvConst.k8s_api_prefix + "nodes";
        ResponseEntity<String> temp = restTemplate.getForEntity(nodeUrl, String.class);
        String jsonStr = temp.getBody();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray array = jsonObject.getJSONArray("items");
        array.forEach(one -> {
            K8s_Node_Metadata tem = (K8s_Node_Metadata) JSONObject.toBean(JSONObject.fromObject(one).getJSONObject("metadata"), K8s_Node_Metadata.class);

            System.out.println(tem);
        });
    }
}
