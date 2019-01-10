package com.sysmin.core.jvm.domain;

/**
 * @author:Li
 * @time: 2019/1/1 15:21
 * @version: 1.0.0
 */
public class JstackDO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 正在线程总数
     */
    private Integer total;

    /**
     * 运行的线程数
     */
    private Integer running;

    /**
     * 等待唤醒的线程数
     */
    private Integer waiting;

    /**
     * 被阻塞的线程数
     * 等待monitor锁(synchronized关键字)的线程数
     */
    private Integer blocked;

    /**
     * 等待唤醒 但设置了时限的线程数
     */
    private Integer timeWaiting;

    /**
     * vm线程总数
     */
    private Integer vmTotal;

    /**
     * 当前时间
     */
    private String date;

    public Integer getPid() {
        return pid;
    }

    public JstackDO setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public JstackDO setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Integer getRunning() {
        return running;
    }

    public JstackDO setRunning(Integer running) {
        this.running = running;
        return this;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public JstackDO setWaiting(Integer waiting) {
        this.waiting = waiting;
        return this;
    }

    public Integer getTimeWaiting() {
        return timeWaiting;
    }

    public JstackDO setTimeWaiting(Integer timeWaiting) {
        this.timeWaiting = timeWaiting;
        return this;
    }

    public Integer getVmTotal() {
        return vmTotal;
    }

    public JstackDO setVmTotal() {
        vmTotal = total - running - waiting - timeWaiting - blocked;
        return this;
    }

    public String getDate() {
        return date;
    }

    public JstackDO setDate(String date) {
        this.date = date;
        return this;
    }

    public Integer getBlocked() {
        return blocked;
    }

    public JstackDO setBlocked(Integer blocked) {
        this.blocked = blocked;
        return this;
    }

    public JstackDO clear() {
        pid = null;
        total = null;
        running = null;
        waiting = null;
        timeWaiting = null;
        blocked = null;
        vmTotal = null;
        date = null;
        return this;
    }

}
