package com.tanli.cloud.utils;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * Created by tanli on 2019/2/9 0009.
 */
public class K8sClient {

    private static final String k8s_api_address = "";

    //构建Kubernetes Client
    public KubernetesClient buildClient(){
        Config config = new ConfigBuilder().withMasterUrl(k8s_api_address).build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        return client;
    }
}
