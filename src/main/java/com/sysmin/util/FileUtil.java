package com.sysmin.util;

import com.sysmin.global.GlobalConfig;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author:Li
 * @time: 2019/1/3 14:05
 * @version: 1.0.0
 */
public class FileUtil {

    /**
     * 生成快照文件名
     *
     * @param pid    进程id
     * @param sonDir 子文件夹
     * @param prefix 文件前缀
     * @param suffix 文件后缀
     * @return 文件名
     */
    public static String checkPath(int pid, String sonDir, String prefix, String suffix) {
        File dumpDir = new File((String) GlobalConfig.getValue("dumpPath") + File.separatorChar + sonDir);
        if (!dumpDir.exists()) {
            if (!dumpDir.getParentFile().exists()) {
                dumpDir.getParentFile().mkdir();
            }
            dumpDir.mkdir();
        }
        return dumpDir.getPath() + File.separatorChar + prefix + pid + "_" + DateUtil.getNowDate("yyyyMMddHHmmss") + suffix;
    }

    /**
     * 读取文件操作
     *
     * @param file 需要读取的文件
     * @return 文件数据
     */
    public static String getFileContent(File file) {
        try {
            return catFile(file);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 读取文件操作
     *
     * @param file 需要读取的文件路径
     * @return 文件数据
     */
    public static String getFileContent(String file) {
        try {
            return catFile(new File(file));
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 读取文件操作
     *
     * @param file 需要读取的文件
     * @return 文件输入流
     */
    public static BufferedReader getFileBuffer(String file) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    /**
     * 读取文件操作
     *
     * @param file 需要读取的文件
     * @return 文件输入流
     */
    public static BufferedReader getFileBuffer(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    /**
     * 文件查看的实现类
     *
     * @param file 文件
     * @return 文件内容
     */
    private static String catFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder s = new StringBuilder();
        String str = null;
        while ((str = br.readLine()) != null) {
            s.append(str + "\n");
        }
        br.close();
        return new String(s);
    }

    /**
     * 获得根目录+输入路径
     *
     * @param path
     * @return
     */
    public static String getRootPath(String path) {
        String rootPath = "";
        String classPath = System.getProperty("user.dir");
        // windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath + path;
            rootPath = rootPath.replaceAll("/", "\\\\");
            if ("\\".equals(rootPath.substring(0, 1))) {
                rootPath = rootPath.substring(1);
            }
        }
        // linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath + path;
            rootPath = rootPath.replaceAll("\\\\", "/");
        }
        return rootPath;
    }

    /**
     * 列出文件夹下的所有文件
     *
     * @param path 路径
     * @return 文件
     */
    public static String[] listFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return new String[0];
        } else {
            String[] tmp = dir.list();
            String[] list = new String[tmp.length];
            for (int i = 0; i < list.length; i++) {
                list[i] = dir.getPath() + File.separatorChar + tmp[i];
            }
            return list;
        }
    }

    /**
     * 将数据存储到文件 utf8编码
     *
     * @param data 数据
     * @param path 文件路径
     * @return 1成功 0失败
     */
    public static int saveDataToFile(String data, String path) {
        return saveDataToFile(data, path, Charset.forName("UTF-8"));
    }

    /**
     * 将数据存储到文件
     *
     * @param data    数据
     * @param path    文件路径
     * @param charset 字符编码
     * @return 1成功 0失败
     */
    public static int saveDataToFile(String data, String path, Charset charset) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), charset));
            writer.write(data);
            writer.newLine();
            writer.flush();
            writer.close();
            return 1;
        } catch (IOException e) {
            return 0;
        }
    }



}
