package com.sysmin.core.linux.process.domain;

/**
 * @author:Li
 * @time: 2018/12/19 21:12
 * @version: 1.0.0
 * ps实体类
 */
public class ProcessDO {

    /**
     * 程序拥有者
     */
    private String user;
    /**
     * 程序id
     */
    private Integer pid;
    /**
     * cpu占用率
     */
    private Double cpu;
    /**
     * 内存占用率
     */
    private Double mem;
    private Integer vsc;
    private Integer rss;
    private String tty;
    private String stat;
    private String start;
    private String time;
    private String command;

    public String getUser() {
        return user;
    }

    public ProcessDO setUser(String user) {
        this.user = user;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public ProcessDO setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public ProcessDO setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMem() {
        return mem;
    }

    public ProcessDO setMem(Double mem) {
        this.mem = mem;
        return this;
    }

    public Integer getVsc() {
        return vsc;
    }

    public ProcessDO setVsc(Integer vsc) {
        this.vsc = vsc;
        return this;
    }

    public Integer getRss() {
        return rss;
    }

    public ProcessDO setRss(Integer rss) {
        this.rss = rss;
        return this;
    }

    public String getTty() {
        return tty;
    }

    public ProcessDO setTty(String tty) {
        this.tty = tty;
        return this;
    }

    public String getStat() {
        return stat;
    }

    public ProcessDO setStat(String stat) {
        this.stat = stat;
        return this;
    }

    public String getStart() {
        return start;
    }

    public ProcessDO setStart(String start) {
        this.start = start;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ProcessDO setTime(String time) {
        this.time = time;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public ProcessDO setCommand(String command) {
        this.command = command;
        return this;
    }

    @Override
    public String toString() {
        return user + "\t" + pid + "\t" + cpu + "\t" + mem + "\t" + vsc + "\t" + rss + "\t" + tty + "\t" + stat + "\t" + start + "\t" + time + "\t" + command;
    }
}
