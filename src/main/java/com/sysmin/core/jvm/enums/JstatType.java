package com.sysmin.core.jvm.enums;

/**
 * @author:Li
 * @time: 2018/12/28 17:03
 * @version: 1.0.0
 * jstat枚举
 */
public enum JstatType {

    /**
     * 统计class loader行为信息
     */
    CLASS,

    /**
     * 统计编译
     */
    COMPILER,

    /**
     * 统计jvm gc信息
     */
    GCUTIL,

    /**
     * 当前VM执行的信息
     */
    PRINTCOMPILATION,

    /**
     * 统计jvm gc时heap信息
     */
    GC;

    /**
     * 将命令封装   默认60秒一次
     *
     * @param pid 进程id
     * @return
     */
    public String getType(int pid) {
        return getType(pid, 60000);
    }

    /**
     * 将命令封装
     *
     * @param pid  进程id
     * @param time 多长时间查询一次 (ms)
     * @return
     */
    public String getType(int pid, int time) {
        switch (this) {
            case CLASS:
                return "jstat -class " + pid + " " + time;
            case COMPILER:
                return "jstat -compiler " + pid + " " + time;
            case GCUTIL:
                return "jstat -gcutil " + pid + " " + time;
            case PRINTCOMPILATION:
                return "jstat -printcompilation " + pid + " " + time;
            case GC:
                return "jstat -gc " + pid + " " + time;
            default:
                return "jstat -gc " + pid + " " + time;
        }
    }

}
