package com.sysmin.core.system.domain;

/**
 * @author:Li
 * @time: 2019/1/22 21:52
 * @version: 1.0.0
 */
public class SystemDO {

    /**
     * 当前ip
     */
    private String ip;

    /**
     * 当前用户
     */
    private String userName;

    /**
     * 计算机名称
     */
    private String hostName;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 运行时间
     */
    private String upTime;


    public String getIp() {
        return ip;
    }

    public SystemDO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SystemDO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public SystemDO setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public SystemDO setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getUpTime() {
        return upTime;
    }

    public SystemDO setUpTime(String upTime) {
        this.upTime = upTime;
        return this;
    }

    @Override
    public String toString() {
        return "SystemDO{" +
                "ip='" + ip + '\'' +
                ", userName='" + userName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", upTime='" + upTime + '\'' +
                '}';
    }
}
