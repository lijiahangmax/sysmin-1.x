package com.sysmin.util;

import com.sysmin.core.jvm.domain.SnapShotDO;
import com.sysmin.global.GlobalConfig;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * 分页获得快照列表信息
     *
     * @param path  文件路径
     * @param page  当前页码
     * @param limit 显示的条数
     * @return 快照信息
     */
    public static ArrayList<SnapShotDO> limitSnapShot(String path, int page, int limit) {
        return limitSnapShot(Arrays.asList(listFile(path)), page, limit);
    }

    /**
     * 分页获得快照列表信息
     *
     * @param paths 文件路径集合
     * @param page  当前页码
     * @param limit 显示的条数
     * @return 快照信息
     */
    public static ArrayList<SnapShotDO> limitSnapShot(List<String> paths, int page, int limit) {
        ArrayList<SnapShotDO> list = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            if (i >= ((page - 1) * limit) && i < ((page - 1) * limit) + limit) {
                String s = paths.get(i);
                String[] names = s.substring(s.lastIndexOf(File.separator) + 1, s.length()).split("_");
                list.add(new SnapShotDO(Integer.valueOf(names[1]), new File(s).length(), DateUtil.dateFormat(names[2].substring(0, names[2].indexOf(".")), "yyyyMMddHHmmss", DateUtil.DEFAULT_FORMAT), s));
            }
        }
        return list;
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
     * 文件查看的实现方法
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
        return replacePath(System.getProperty("user.dir") + path);
    }

    /**
     * 将路径 替换为系统路径
     *
     * @param path
     * @return
     */
    public static String replacePath(String path) {
        // windows下
        if ("\\".equals(File.separator)) {
            path = path.replaceAll("/", "\\\\");
            if ("\\".equals(path.substring(0, 1))) {
                path = path.substring(1);
            }
        }
        // linux下
        if ("/".equals(File.separator)) {
            path = path.replaceAll("\\\\", "/");
        }
        return path;
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
            File file = new File(path);
            if (file.exists()) {
                return 0;
            } else if (!file.createNewFile()) {
                return 0;
            }
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
