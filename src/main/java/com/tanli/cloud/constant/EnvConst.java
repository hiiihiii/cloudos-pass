package com.tanli.cloud.constant;

import org.springframework.stereotype.Component;

@Component
public class EnvConst {
    /**
     * ftp服务器配置
     */
    public static final String FTP_IP = "132.232.140.33";
    public static final int FTP_PORT = 21;
    public static final String FTP_USERNAME = "docker";
    public static final String FTP_PASSWORD = "dockerfile";
    public static final String FTP_BASEPATH = "/var/ftp";


}
