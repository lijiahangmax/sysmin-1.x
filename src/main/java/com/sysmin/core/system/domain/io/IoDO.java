package com.sysmin.core.system.domain.io;

import java.util.ArrayList;

/**
 * @author:Li
 * @time: 2018/12/26 8:52
 * @version: 1.0.0
 * iostat 获得cpu报告以及io报告
 * iostat -cdxm -t 1
 * mpstat可以单独查看cpu信息
 */
public class IoDO {

    /**
     * cpu信息
     */
    private CpuInfo cpu;

    /**
     * 磁盘信息
     */
    private ArrayList<IoInfo> io;

    public CpuInfo getCpu() {
        return cpu;
    }

    public IoDO setCpu(CpuInfo cpu) {
        this.cpu = cpu;
        return this;
    }

    public ArrayList<IoInfo> getIo() {
        return io;
    }

    public IoDO setIo(ArrayList<IoInfo> io) {
        this.io = io;
        return this;
    }

    @Override
    public String toString() {
        return "IoDO{" +
                "cpu=" + cpu +
                ", io=" + io +
                '}';
    }

    public void clear() {
        cpu = null;
        io = null;
    }

}
