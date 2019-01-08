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
     * 线程总数
     */
    private Integer total;

    /**
     * 运行的线程数
     */
    private Integer running;

    /**
     * 等待的线程数
     */
    private Integer waiting;

    /**
     * 等待的线程数 (自动释放)
     */
    private Integer timeWaiting;

    /**
     * vm线程
     */
    private Integer vmToatl;

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

    public Integer getVmToatl() {
        return vmToatl;
    }

    public JstackDO setVmToatl(Integer vmToatl) {
        this.vmToatl = vmToatl;
        return this;
    }

    public JstackDO setVmToatl() {
        vmToatl = total - running - waiting - timeWaiting;
        return this;
    }

    @Override
    public String toString() {
        return "JstackDO{" +
                "pid=" + pid +
                ", total=" + total +
                ", running=" + running +
                ", waiting=" + waiting +
                ", timeWaiting=" + timeWaiting +
                ", vmToatl=" + vmToatl +
                '}';
    }

    public JstackDO clear() {
        pid = null;
        total = null;
        running = null;
        waiting = null;
        timeWaiting = null;
        vmToatl = null;
        return this;
    }

}
