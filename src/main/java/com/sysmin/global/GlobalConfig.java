package com.sysmin.global;

import com.sysmin.core.log.Log;
import com.sysmin.core.system.SystemInfo;
import com.sysmin.util.BashUtil;
import com.sysmin.util.FileUtil;
import com.sysmin.util.StringUtil;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:Li
 * @time: 2018/12/18 8:56
 * @version: 1.0.0
 */
public class GlobalConfig {

    /**
     * 全局容器
     */
    private static Map<String, Object> container = new HashMap<>();

    static {
        new SystemInfo();
        container.put("logPath", FileUtil.getRootPath("/log/"));
        container.put("shPath", FileUtil.getRootPath("/sh/"));
        container.put("dumpPath", FileUtil.getRootPath("/dump/"));
    }

    /**
     * 获取数据
     *
     * @param key key
     * @return value
     */
    public static Object getValue(String key) {
        return container.get(key);
    }

    /**
     * 插入数据
     *
     * @param key   key
     * @param value value
     * @param cover 有值是否覆盖
     */
    public static void setValue(String key, Object value, boolean cover) {
        if (cover) {
            container.put(key, value);
            return;
        }
        if (!container.containsKey(key)) {
            container.put(key, value);
        }
    }

    /**
     * put数据 如果有会覆盖原来数据
     *
     * @param key   key
     * @param value value
     */
    public static void setValue(String key, Object value) {
        setValue(key, value, true);
    }

    /**
     * 通过k和v删除
     *
     * @param key   key
     * @param value value
     * @return 是否删除成功
     */
    public static boolean removeKey(String key, Object value) {
        return container.remove(key, value);
    }

    /**
     * 通过key删除
     *
     * @param key key
     */
    public static void removeKey(String key) {
        container.remove(key);
    }

    /**
     * 获得系统属性
     *
     * @return
     */
    private static String getProperty(String key) {
        return System.getProperty(key);
    }

    public static void main(String[] args) throws Exception {
        // Log.getLog("user", "log", "remake").info();
        new Thread(() -> {
        }).start();
    }

}
