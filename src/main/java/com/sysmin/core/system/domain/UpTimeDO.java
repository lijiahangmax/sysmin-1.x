package com.sysmin.core.system.domain;

import java.util.Arrays;

/**
 * @author:Li
 * @time: 2018/12/28 15:32
 * @version: 1.0.0
 * uptime
 */
public class UpTimeDO {

    /**
     * 系统当前时间
     */
    private String systemTime;

    /**
     * 主机已运行时间
     */
    private String upTime;

    /**
     * 用户连接数
     */
    private Integer users;

    /**
     * 系统平均负载，统计最近1，5，15分钟的系统平均负载
     */
    private Double[] average;

    public String getSystemTime() {
        return systemTime;
    }

    public UpTimeDO setSystemTime(String systemTime) {
        this.systemTime = systemTime;
        return this;
    }

    public String getUpTime() {
        return upTime;
    }

    public UpTimeDO setUpTime(String upTime) {
        this.upTime = upTime;
        return this;
    }

    public Integer getUsers() {
        return users;
    }

    public UpTimeDO setUsers(Integer users) {
        this.users = users;
        return this;
    }

    public Double[] getAverage() {
        return average;
    }

    public UpTimeDO setAverage(Double[] average) {
        this.average = average;
        return this;
    }

    @Override
    public String toString() {
        return "UpTimeDO{" +
                "systemTime='" + systemTime + '\'' +
                ", upTime='" + upTime + '\'' +
                ", users='" + users + '\'' +
                ", average=" + Arrays.toString(average) +
                '}';
    }
}
