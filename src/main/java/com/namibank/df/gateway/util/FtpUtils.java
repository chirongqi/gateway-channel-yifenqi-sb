package com.namibank.df.gateway.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * 下载文件
     * 
     * @param host
     * @param userName
     * @param password
     * @param remotePath 远程文件地址
     * @param localPath 本地文件地址
     * @return
     */
    public static boolean downloadFile(String host, String userName, String password, String remotePath, String localPath) {
        boolean success = false;
        FTPClient client = new FTPClient();
        try {
            int replyCode;
            client.setConnectTimeout(60 * 1000);
            client.connect(host);
            logger.info(">>>>>>>>>> FTP client isConnected: " + client.isConnected());
            client.login(userName, password);// 登录
            replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                disconnect(client);
                logger.info(">>>>>>>>>> FTP ReplyCode: {}, fail", replyCode);
                return success;
            }
            logger.info(">>>>>>>>>> RemoteAddress: {}", client.getRemoteAddress());
            FileOutputStream out = new FileOutputStream(new File(localPath));
            client.enterLocalPassiveMode();
            success = client.retrieveFile(remotePath, out);
        } catch (Exception e) {
            logger.error("downloadFile Exception", e);
        } finally {
            logout(client);
            disconnect(client);
        }
        return success;
    }

    private static void logout(FTPClient client) {
        try {
            if (client != null && client.isConnected()) {
                client.logout();
            }
        } catch (Exception e) {
            logger.error("logout Exception", e);
        }
    }

    private static void disconnect(FTPClient client) {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
            }
        } catch (Exception e) {
            logger.error("disconnect Exception", e);
        }
    }
}
