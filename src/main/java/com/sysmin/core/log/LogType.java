package com.sysmin.core.log;

/**
 * @author:Li
 * @time: 2019/1/3 17:49
 * @version: 1.0.0
 * 日志级别
 */
public enum LogType {

    /**
     * error
     */
    ERROR,

    /**
     * warn
     */
    WARN,

    /**
     * info
     */
    INFO;

    /**
     * 获得日志级别
     *
     * @return
     */
    public String getType() {
        switch (this) {
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            default:
                return "INFO";
        }
    }

}
