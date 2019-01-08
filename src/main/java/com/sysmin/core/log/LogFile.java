package com.sysmin.core.log;

import com.sysmin.global.GlobalConfig;
import com.sysmin.util.DateUtil;
import com.sysmin.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:Li
 * @time: 2018/12/20 11:18
 * @version: 1.0.0
 */
public class LogFile {

    /**
     * 日志大小 kb
     */
    private static final int LOG_SIZE = 30;

    /**
     * 获得所有日志文件
     *
     * @return
     */
    public static String[] listLogFile() {
        return FileUtil.listFile((String) GlobalConfig.getValue("logPath"));
    }

    /**
     * 获得日志文件的内容
     *
     * @param path 路径
     * @return
     */
    public static String loadLogFile(String path) {
        return FileUtil.getFileContent(path);
    }

    /**
     * 获得日志文件
     *
     * @return
     */
    public static Map<String, Object> createLogFile() {
        return createLogFile(1);
    }

    /**
     * 获得日志文件
     *
     * @param index 文件索引
     * @return free: 该文件可用空间 该文件地址
     */
    private static Map<String, Object> createLogFile(int index) {
        Map<String, Object> map = new HashMap<>(2);
        String logPath = (String) GlobalConfig.getValue("logPath");
        String logFileTemp = "log " + DateUtil.getNowDate("yyyyMMdd") + " " + index + ".log";
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File logFile = new File(logPath + logFileTemp);
        if (logFile.exists() && logFile.isFile()) {
            int size = Integer.valueOf(logFile.length() / 1024 + "");
            if (size >= LOG_SIZE) {
                return createLogFile(index + 1);
            } else {
                map.put("free", LOG_SIZE - size);
            }
        } else {
            try {
                logFile.createNewFile();
                map.put("free", LOG_SIZE);
            } catch (Exception e) {
                System.out.println("error create file" + logFile.getPath());
            }
        }
        map.put("path", logFile.getPath());
        return map;
    }

}
