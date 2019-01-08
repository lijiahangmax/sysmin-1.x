package com.sysmin.core.system.domain.io;

/**
 * @author:Li
 * @time: 2018/12/28 9:01
 * @version: 1.0.0
 */
public class CpuInfo {

    /**
     * CPU在用户态执行进程的时间百分比
     */
    private Double user;

    /**
     * CPU在用户态模式下，用于nice操作，所占用CPU总时间的百分比
     */
    private Double nice;

    /**
     * CPU处在内核态执行进程的时间百分比
     */
    private Double system;

    /**
     * CPU用于等待I/O操作占用CPU总时间的百分比
     * 值过高，表示硬盘存在I/O瓶颈
     */
    private Double iowait;

    /**
     * 管理程序(hypervisor)为另一个虚拟进程提供服务而等待虚拟CPU的百分比
     */
    private Double steal;

    /**
     * CPU空闲时间百分比
     * 值高但系统响应慢时，有可能是CPU等待分配内存，此时应加大内存容量
     */
    private Double idle;

    public Double getUser() {
        return user;
    }

    public CpuInfo setUser(Double user) {
        this.user = user;
        return this;
    }

    public Double getNice() {
        return nice;
    }

    public CpuInfo setNice(Double nice) {
        this.nice = nice;
        return this;
    }

    public Double getSystem() {
        return system;
    }

    public CpuInfo setSystem(Double system) {
        this.system = system;
        return this;
    }

    public Double getIowait() {
        return iowait;
    }

    public CpuInfo setIowait(Double iowait) {
        this.iowait = iowait;
        return this;
    }

    public Double getSteal() {
        return steal;
    }

    public CpuInfo setSteal(Double steal) {
        this.steal = steal;
        return this;
    }

    public Double getIdle() {
        return idle;
    }

    public CpuInfo setIdle(Double idle) {
        this.idle = idle;
        return this;
    }

    @Override
    public String toString() {
        return "Cpu{" +
                "user=" + user +
                ", nice=" + nice +
                ", system=" + system +
                ", iowait=" + iowait +
                ", steal=" + steal +
                ", idle=" + idle +
                '}';
    }

    public void clear() {
        user = null;
        nice = null;
        system = null;
        iowait = null;
        steal = null;
        idle = null;
    }

}