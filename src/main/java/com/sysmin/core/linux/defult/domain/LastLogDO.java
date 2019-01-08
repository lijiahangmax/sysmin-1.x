package com.sysmin.core.linux.defult.domain;

/**
 * @author:Li
 * @time: 2018/12/25 15:53
 * @version: 1.0.0
 */
public class LastLogDO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 端口
     */
    private String port;

    /**
     * 来自
     */
    private String from;

    /**
     * 最后登录时间
     */
    private String lastTime;

    public LastLogDO() {
    }

    public LastLogDO(String username, String port, String from, String lastTime) {
        this.username = username;
        this.port = port;
        this.from = from;
        this.lastTime = lastTime;
    }

    public String getUsername() {
        return username;
    }

    public LastLogDO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPort() {
        return port;
    }

    public LastLogDO setPort(String port) {
        this.port = port;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public LastLogDO setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getLastTime() {
        return lastTime;
    }

    public LastLogDO setLastTime(String lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    @Override
    public String toString() {
        //  return username + "\t" + port + "\t" + from + "\t" + lastTime;
        return "LastLogDO{" +
                "username='" + username + '\'' +
                ", port='" + port + '\'' +
                ", from='" + from + '\'' +
                ", lastTime='" + lastTime + '\'' +
                '}';
    }
}
