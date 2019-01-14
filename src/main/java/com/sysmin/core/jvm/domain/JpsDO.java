package com.sysmin.core.jvm.domain;

import java.util.Arrays;

/**
 * @author:Li
 * @time: 2018/12/28 16:23
 * @version: 1.0.0
 * jps -v
 */
public class JpsDO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 进程名称
     */
    private String name;

    /**
     * 参数信息
     */
    private String[] flags;

    public JpsDO() {
    }

    public JpsDO(Integer pid, String name, String[] flags) {
        this.pid = pid;
        this.name = name;
        this.flags = flags;
    }

    public Integer getPid() {
        return pid;
    }

    public JpsDO setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public JpsDO setName(String name) {
        this.name = name;
        return this;
    }

    public String[] getFlags() {
        return flags;
    }

    public JpsDO setFlags(String[] flags) {
        this.flags = flags;
        return this;
    }

    @Override
    public String toString() {
        return "JpsDO{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", flags=" + Arrays.toString(flags) +
                '}';
    }

    public JpsDO clear() {
        pid = null;
        name = null;
        flags = null;
        return this;
    }

}
