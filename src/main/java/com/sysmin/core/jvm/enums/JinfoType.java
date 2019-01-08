package com.sysmin.core.jvm.enums;

/**
 * @author:Li
 * @time: 2018/12/31 19:17
 * @version: 1.0.0
 */
public enum JinfoType {

    /**
     * vm参数
     */
    FLAGS;

    /**
     * 命令类型
     * 0:flags
     */
    private int type;

    public int getType() {
        return type;
    }

    /**
     * 将命令封装
     *
     * @param pid 进程id
     * @return
     */
    public String getType(int pid) {
        switch (this) {
            case FLAGS:
                type = 0;
                return "jinfo -flags " + pid;
            default:
                type = 0;
                return "jinfo -flags " + pid;
        }
    }

}
