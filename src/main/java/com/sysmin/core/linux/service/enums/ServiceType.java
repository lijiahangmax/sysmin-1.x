package com.sysmin.core.linux.service.enums;

/**
 * @author:Li
 * @time: 2018/12/24 13:25
 * @version: 1.0.0
 */
public enum ServiceType {
    /**
     * 启动
     */
    START,
    /**
     * 停止
     */
    STOP,
    /**
     * 状态
     */
    STATUS,
    /**
     * 重启
     */
    RESTART;

    public String getType() {
        switch (this) {
            case START:
                return "start";
            case STOP:
                return "stop";
            case STATUS:
                return "status";
            case RESTART:
                return "restart";
            default:
                return "start";
        }
    }

}
