package com.sysmin.core.jvm.enums;

import com.sysmin.global.GlobalConfig;

/**
 * @author:Li
 * @time: 2018/12/31 19:16
 * @version: 1.0.0
 */
public enum JstackType {

    /**
     * 默认查看
     */
    DEFAULT;


    /**
     * 将命令封装
     *
     * @param pid 进程id
     * @return
     */
    public String getType(int pid) {
        switch (this) {
            case DEFAULT:
                return "jstack " + pid;
            default:
                return "jstack " + pid;
        }
    }

    /**
     * 转储到本地
     *
     * @param pid  进程id
     * @param path 文件路径
     * @return
     */
    public String getType(int pid, String path) {
        if (((String) GlobalConfig.getValue("OSSystem")).contains("Linux")) {
            switch (this) {
                case DEFAULT:
                    return "jstack " + pid + " > " + path;
                default:
                    return "jstack " + pid + " > " + path;
            }
        } else {
            return getType(pid);
        }
    }

}
