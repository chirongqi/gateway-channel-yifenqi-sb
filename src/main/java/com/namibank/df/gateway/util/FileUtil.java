package com.namibank.df.gateway.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类,继承于FileUtils
 * 
 * @author CliveYuan
 * @date Apr 6, 2017 10:51:36 AM
 *
 */
public class FileUtil extends FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);



    /**
     * 删除文件、文件夹
     *
     * @return
     */
    public static boolean deleteFile(String path) {
        //logger.info("[Deleting...] {}", path);
        boolean result = false;
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] ff = file.listFiles();
                for (int i = 0; i < ff.length; i++) {
                    deleteFile(ff[i].getPath());
                }
            }
            result = file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * printFileNames: 遍历文件夹 <br/>
     * @author Clive Yuan
     * @param path
     */
    public static void printFileNames(String path) {
//        String path = "D:/shift/mine/tucano/tucano.base.standalone/libs";
        File folder = new File(path);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (!file.isDirectory()) {
                    //logger.info(file.getName());
                    System.out.println(file.getName());
                }
            }
        }
    }

    /**
     * 读取文件内容 返回字符串
     * @param filePath
     * @return
     */
    public static String readFile(String filePath,boolean isByLine){
         String content = "";
            try {
                    String encoding="utf-8";
                    File file=new File(filePath);
                    if(file.isFile() && file.exists()){ //判断文件是否存在
                        InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                        BufferedReader bufferedReader = new BufferedReader(read);
                        String lineTxt = null;
                        while((lineTxt = bufferedReader.readLine()) != null){
                            content += lineTxt;
                            if(isByLine)
                                content += "\n";
                        }
                        read.close();
            }else{
                logger.error("找不到指定的文件:"+filePath);
            }
            } catch (Exception e) {
                logger.error("读取文件内容出错:"+filePath ,e);
            }
         return content;
        }

    /**
    * 检测网络资源是否存在
    *
    * @param strUrl
    * @return
    */
    public static boolean isNetFileAvailable(String strUrl) {
        InputStream netFileInputStream = null;
        try {
            URL url = new URL(strUrl);
            URLConnection urlConn = url.openConnection();
            netFileInputStream = urlConn.getInputStream();
            if (null != netFileInputStream) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
                return false;
        } finally {
            try {
                if (netFileInputStream != null)
                    netFileInputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 不存在则创建全路径文件夹
     * @param path
     * @return
     */
    public static void createFolders(String path) {
        if (path.contains("\\")) {
            path = path.replaceAll("\\\\", "/");
        }
        File file = new File(path);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }

    public static boolean exists(String path) {
        if (path.contains("\\")) {
            path = path.replaceAll("\\\\", "/");
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * B方法追加文件：使用FileWriter
     */
    public static void append(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 重命名文件夹
     * @author CliveYuan
     * @date Aug 3, 2016
     * @param oldPath
     * @param newPath
     */
    public static void renameFolder(String oldPath, String newPath) {
        File dir = new File(oldPath);
        File newName = new File(newPath);
        if (dir.isDirectory()) {
                dir.renameTo(newName);
        }
    }

    /**
     * 文件重命名
     *
     * @param path
     *            文件目录
     * @param oldname
     *            原来的文件名
     * @param newname
     *            新文件名
     */
    public static void renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            if (oldfile.exists() && !newfile.exists()) {
                oldfile.renameTo(newfile);
            }
        }
    }

    public static boolean writeFile(String filePath, String data) {

        File file = new File(filePath);
        if (file.isDirectory()) {
            createFolders(filePath);
        }
        try {
            FileUtils.write(file, data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void copyDir(String tepmlatePath, String newProjectPath) throws Exception {
        File srcDir = new File(tepmlatePath);
        File destDir = new File(newProjectPath);
        copyDirectory(srcDir, destDir);
    }
}
