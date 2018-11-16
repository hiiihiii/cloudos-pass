package com.tanli.cloud.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FtpUtil {

    private static final Logger LOGGE = LoggerFactory.getLogger(FtpUtil.class);
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "132.232.140.33";//腾讯云服务器
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "docker";
//    private static final String FTP_USERNAME = "root";
    //密码
    private static final String FTP_PASSWORD = "dockerfile";
//    private static final String FTP_PASSWORD = "i?3Q!8Laztan_li";

    //文件路径
    public final String FTP_BASEPATH = "/var/ftp";

    public String uploadFile(String fileName, InputStream input, String relativePath, String newFileName){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return "";
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //将客户端设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //ftpClient.changeWorkingDirectory(FTP_BASEPATH + relativePath);
            ftpClient.changeWorkingDirectory( relativePath);
            Boolean uploadResult = ftpClient.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"), input);
            String result = "";
            if( uploadResult ){
                //修改文件名(appName+version.扩展名)
                ftpClient.rename(fileName, newFileName);
                result = FTP_ADDRESS + FTP_BASEPATH + relativePath + newFileName;
            }
            input.close();
            ftpClient.logout();
            return result;
        } catch (IOException e){
            LOGGE.info("[FtpUtil Error]: "+ "上传" + fileName + "失败");
            e.printStackTrace();
        } catch (NullPointerException n){
            LOGGE.info("[FtpUtil Error]: "+ "上传" + fileName + "失败");
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return "";
    }
}
