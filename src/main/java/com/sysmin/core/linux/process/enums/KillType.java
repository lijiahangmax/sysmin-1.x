package com.sysmin.core.linux.process.enums;

/**
 * @author:Li
 * @time: 2018/12/21 14:35
 * @version: 1.0.0
 * kill 枚举级别
 */
public enum KillType {
    /**
     * 1 终端断线
     */
    HUP,
    /**
     * 2 中断（同 Ctrl + C）
     */
    INT,
    /**
     * 3 退出（同 Ctrl + \）
     */
    QUIT,
    /**
     * 15 终止
     */
    TERM,
    /**
     * 9 强制终止
     */
    KILL,
    /**
     * 18 继续（与STOP相反， fg/bg命令）
     */
    CONT,
    /**
     * 19 暂停（同 Ctrl + Z）
     */
    STOP;

    public int getType() {
        switch (this) {
            case HUP:
                return 1;
            case INT:
                return 2;
            case KILL:
                return 9;
            case QUIT:
                return 3;
            case TERM:
                return 15;
            case CONT:
                return 18;
            case STOP:
                return 19;
            default:
                return 9;
        }
    }
}
