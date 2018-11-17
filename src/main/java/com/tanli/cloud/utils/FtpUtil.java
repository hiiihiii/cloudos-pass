package com.tanli.cloud.utils;

import com.tanli.cloud.constant.Environment;
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

    public String uploadFile(String fileName, InputStream input, String relativePath, String newFileName){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            ftpClient.connect(Environment.FTP_IP, Environment.FTP_PORT);
            ftpClient.login(Environment.FTP_USERNAME, Environment.FTP_PASSWORD);
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
                result = Environment.FTP_IP + Environment.FTP_BASEPATH + relativePath + "/" + newFileName;
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
