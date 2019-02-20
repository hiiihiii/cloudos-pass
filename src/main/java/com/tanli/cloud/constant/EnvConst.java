package com.tanli.cloud.constant;

import org.springframework.stereotype.Component;

@Component
public class EnvConst {
    /**
     * ftp服务器配置
     */
    public static final String FTP_IP = "132.232.93.3";
    public static final int FTP_PORT = 21;
    public static final String FTP_USERNAME = "docker";
    public static final String FTP_PASSWORD = "dockerfile";
    public static final String FTP_BASEPATH = "/var/ftp";

    /**
     * docker remote api配置
     */
    public static final String docker_api_protocol = "http";
    public static final String docker_api_ip = "132.232.93.3";
    public static final String docker_api_port = "2375";
    public static final String docker_images_prefix = "http://132.232.93.3:2375/images/";
    public static final String harbor_username = "admin";
    public static final String harbor_password = "Harbor12345";

    /**
     * kubernetes api配置
     */
    public static final String k8s_api_protocol = "http";
    public static final String k8s_api_ip = "132.232.99.189";
    public static final String k8s_api_port = "8080";
    public static final String k8s_api_prefix = "http://132.232.99.189:8080/api/v1/";

    public static final String vip = "132.232.56.2";//虚ip，最好是可以动态获取

}
