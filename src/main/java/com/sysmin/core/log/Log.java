package com.sysmin.core.log;

import com.sysmin.util.DateUtil;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author:Li
 * @time: 2018/12/18 19:05
 * @version: 1.0.0
 */
public class Log {

    /**
     * 日志容器
     */
    public static ArrayList<Log> logList = new ArrayList<Log>();

    /**
     * 储存到本地的日志容器
     */
    public static ArrayList<Log> cacheLog = new ArrayList<Log>();

    /**
     * id自增
     */
    private static long idCounter = 0L;

    /**
     * 添加日志到容器
     */
    private Log addLog() {
        logList.add(this);
        return this;
    }

    /**
     * id计数器
     */
    private long idCounter() {
        idCounter++;
        return idCounter;
    }

    /**
     * 添加临时日志到容器
     */
    private void addCacheLog() {
        cacheLog.add(this);
    }

    /**
     * 防止实例
     */
    private Log() {
    }


    /**
     * 将数据转化为log对象
     *
     * @return log对象
     */
    public static Log getLogContent(long id, String logType, String user, String createTime, String remake, String log) {
        return new Log().setId(id)
                .setLogType(logType)
                .setUser(user)
                .setCreateTime(createTime)
                .setRemake(remake)
                .setLog(log);
    }

    /**
     * 实例方法
     */
    public static void getLog(String user, String log, String remake) {
        getLog(user, log, remake, LogType.INFO);
    }

    public static void getLog(String user, String log, String remake, LogType type) {
        new Log()
                .setId()
                .setUser(user)
                .setLog(log)
                .setRemake(remake)
                .setCreateTime()
                .setLogType(type.getType())
                .addLog()
                .addCacheLog();
    }

    /**
     * 日志id
     */
    private long id;

    /**
     * 日志级别
     */
    private String logType;

    /**
     * 操作用户
     */
    private String user;

    /**
     * 日志信息
     */
    private String log;

    /**
     * 操作简述
     */
    private String remake;

    /**
     * 操作时间
     */
    private String createTime;

    public long getId() {
        return id;
    }

    private Log setId() {
        id = idCounter();
        return this;
    }

    private Log setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogType() {
        return logType;
    }

    private Log setLogType(String logType) {
        this.logType = logType;
        return this;
    }

    public String getLog() {
        return log;
    }

    private Log setLog(String log) {
        this.log = log;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    private Log setCreateTime() {
        this.createTime = DateUtil.getNowDate(DateUtil.DEFAULT_FORMAT);
        return this;
    }

    private Log setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getRemake() {
        return remake;
    }

    private Log setRemake(String remake) {
        this.remake = remake;
        return this;
    }

    public String getUser() {
        return user;
    }

    private Log setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return id + "\t" + logType + "\t" + user + "\t" + createTime + "\t" + remake + "\t" + log;
    }

    /**
     * 持久化到本地
     */
    public static void cache() {
        cache(cacheLog.size());
    }

    private static void cache(int index) {
        StringBuilder sb = new StringBuilder();
        if (cacheLog.isEmpty()) {
            return;
        }
        for (int i = 0; i < index; i++) {
            sb.append(cacheLog.get(i) + "\n");
        }
        try {
            Map<String, Object> logFile = LogFile.createLogFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((String) logFile.get("path"), true)));
            String data = sb.toString();
            double dataLength = Double.valueOf(data.getBytes().length / 1024);
            double freeLength = Double.valueOf((int) logFile.get("free"));
            if (dataLength > freeLength) {
                cache((int) Math.floor(cacheLog.size() * (1 - ((dataLength - freeLength) / dataLength))));
            } else {
                writer.write(data);
                writer.close();
                if (index == cacheLog.size()) {
                    cacheLog.clear();
                } else {
                    for (int i = 0; i < index; i++) {
                        cacheLog.remove(0);
                    }
                    cache();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
