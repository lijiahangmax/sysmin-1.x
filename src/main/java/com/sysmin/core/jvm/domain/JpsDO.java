package com.sysmin.core.jvm.domain;

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


    @Override
    public String toString() {
        return "JpsDO{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                '}';
    }

    public JpsDO clear() {
        pid = null;
        name = null;
        return this;
    }

}
