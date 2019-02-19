package com.sysmin.core.system.domain;

/**
 * @author:Li
 * @time: 2018/12/26 9:55
 * @version: 1.0.0
 * free -s 1
 */
public class MemoryDO {

    /**
     * 物理内存总量total=used + free
     */
    private Long memTotal;

    /**
     * 已使用的内存总量，包含应用使用量 + buffer + cached
     */
    private Long memUsed;

    /**
     * 空闲内存总量
     */
    private Long memFree;

    /**
     * 共享内存总量
     */
    private Long memShared;

    /**
     * 块设备所占用的缓存
     */
    private Long memBuffers;

    /**
     * 普通文件数据所占用的缓存
     */
    private Long memCached;

    /**
     * 正在使用的内存总量 已使用内存（used）减去buffer和cached之后的内存
     */
    private Long memBeUsed;

    /**
     * 真正的可用内存总量 空闲内存加上buffer和cached之后的内存
     */
    private Long memBeFree;

    /**
     * 交换分区内存总量
     */
    private Long swapTotal;

    /**
     * 正在使用的交换分区内存
     */
    private Long swapUsed;

    /**
     * 空闲交换分区内存
     */
    private Long swapFree;

    public Long getMemTotal() {
        return memTotal;
    }

    public MemoryDO setMemTotal(Long memTotal) {
        this.memTotal = memTotal;
        return this;
    }

    public Long getMemUsed() {
        return memUsed;
    }

    public MemoryDO setMemUsed(Long memUsed) {
        this.memUsed = memUsed;
        return this;
    }

    public Long getMemFree() {
        return memFree;
    }

    public MemoryDO setMemFree(Long memFree) {
        this.memFree = memFree;
        return this;
    }

    public Long getMemShared() {
        return memShared;
    }

    public MemoryDO setMemShared(Long memShared) {
        this.memShared = memShared;
        return this;
    }

    public Long getMemBuffers() {
        return memBuffers;
    }

    public MemoryDO setMemBuffers(Long memBuffers) {
        this.memBuffers = memBuffers;
        return this;
    }

    public Long getMemCached() {
        return memCached;
    }

    public MemoryDO setMemCached(Long memCached) {
        this.memCached = memCached;
        return this;
    }

    public Long getMemBeUsed() {
        return memBeUsed;
    }

    public MemoryDO setMemBeUsed(Long memBeUsed) {
        this.memBeUsed = memBeUsed;
        return this;
    }

    public Long getMemBeFree() {
        return memBeFree;
    }

    public MemoryDO setMemBeFree(Long memBeFree) {
        this.memBeFree = memBeFree;
        return this;
    }

    public Long getSwapTotal() {
        return swapTotal;
    }

    public MemoryDO setSwapTotal(Long swapTotal) {
        this.swapTotal = swapTotal;
        return this;
    }

    public Long getSwapUsed() {
        return swapUsed;
    }

    public MemoryDO setSwapUsed(Long swapUsed) {
        this.swapUsed = swapUsed;
        return this;
    }

    public Long getSwapFree() {
        return swapFree;
    }

    public MemoryDO setSwapFree(Long swapFree) {
        this.swapFree = swapFree;
        return this;
    }

    @Override
    public String toString() {
        return "FreeDo{" +
                "memTotal=" + memTotal +
                ", memUsed=" + memUsed +
                ", memFree=" + memFree +
                ", memShared=" + memShared +
                ", memBuffers=" + memBuffers +
                ", memCached=" + memCached +
                ", memBeUsed=" + memBeUsed +
                ", memBeFree=" + memBeFree +
                ", swapTotal=" + swapTotal +
                ", swapUsed=" + swapUsed +
                ", swapFree=" + swapFree +
                '}';
    }

    public void clear() {
        memTotal = null;
        memUsed = null;
        memFree = null;
        memShared = null;
        memBuffers = null;
        memCached = null;
        memBeUsed = null;
        memBeFree = null;
        swapTotal = null;
        swapUsed = null;
        swapFree = null;
    }

}
