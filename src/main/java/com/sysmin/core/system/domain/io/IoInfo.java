package com.sysmin.core.system.domain.io;

/**
 * @author:Li
 * @time: 2018/12/28 9:01
 * @version: 1.0.0
 */
public class IoInfo {

    /**
     * 设备名称
     */
    private String device;

    /**
     * 每秒对该设备的读请求被合并次数，文件系统会对读取同块(block)的请求进行合并
     */
    private Double rrqm;

    /**
     * 每秒对该设备的写请求被合并次数
     */
    private Double wrqm;

    /**
     * 每秒完成的读次数
     */
    private Double r;

    /**
     * 每秒完成的写次数
     */
    private Double w;

    /**
     * 每秒读数据量(mb为单位)
     */
    private Double rMB;

    /**
     * 每秒写数据量(mb为单位)
     */
    private Double wMB;

    /**
     * 平均每次IO操作的数据量(扇区数为单位)
     */
    private Double avgrq;

    /**
     * 平均等待处理的IO请求队列长度
     */
    private Double avgqu;

    /**
     * 平均每次IO请求等待时间(包括等待时间和处理时间，毫秒为单位)
     */
    private Double await;

    /**
     * 平均每次IO读请求等待时间(包括等待时间和处理时间，毫秒为单位)
     */
    private Double rAwait;

    /**
     * 平均每次IO写请求等待时间(包括等待时间和处理时间，毫秒为单位)
     */
    private Double wAwait;

    /**
     * 平均每次IO请求的处理时间(毫秒为单位)
     */
    private Double svctm;

    /**
     * 采用周期内用于IO操作的时间比率，即IO队列非空的时间比率
     */
    private Double util;

    public String getDevice() {
        return device;
    }

    public IoInfo setDevice(String device) {
        this.device = device;
        return this;
    }

    public Double getRrqm() {
        return rrqm;
    }

    public IoInfo setRrqm(Double rrqm) {
        this.rrqm = rrqm;
        return this;
    }

    public Double getWrqm() {
        return wrqm;
    }

    public IoInfo setWrqm(Double wrqm) {
        this.wrqm = wrqm;
        return this;
    }

    public Double getR() {
        return r;
    }

    public IoInfo setR(Double r) {
        this.r = r;
        return this;
    }

    public Double getW() {
        return w;
    }

    public IoInfo setW(Double w) {
        this.w = w;
        return this;
    }

    public Double getrMB() {
        return rMB;
    }

    public IoInfo setrMB(Double rMB) {
        this.rMB = rMB;
        return this;
    }

    public Double getwMB() {
        return wMB;
    }

    public IoInfo setwMB(Double wMB) {
        this.wMB = wMB;
        return this;
    }

    public Double getAvgrq() {
        return avgrq;
    }

    public IoInfo setAvgrq(Double avgrq) {
        this.avgrq = avgrq;
        return this;
    }

    public Double getAvgqu() {
        return avgqu;
    }

    public IoInfo setAvgqu(Double avgqu) {
        this.avgqu = avgqu;
        return this;
    }

    public Double getAwait() {
        return await;
    }

    public IoInfo setAwait(Double await) {
        this.await = await;
        return this;
    }

    public Double getrAwait() {
        return rAwait;
    }

    public IoInfo setrAwait(Double rAwait) {
        this.rAwait = rAwait;
        return this;
    }

    public Double getwAwait() {
        return wAwait;
    }

    public IoInfo setwAwait(Double wAwait) {
        this.wAwait = wAwait;
        return this;
    }

    public Double getSvctm() {
        return svctm;
    }

    public IoInfo setSvctm(Double svctm) {
        this.svctm = svctm;
        return this;
    }

    public Double getUtil() {
        return util;
    }

    public IoInfo setUtil(Double util) {
        this.util = util;
        return this;
    }

    @Override
    public String toString() {
        return "IoInfo{" +
                "device='" + device + '\'' +
                ", rrqm=" + rrqm +
                ", wrqm=" + wrqm +
                ", r=" + r +
                ", w=" + w +
                ", rMB=" + rMB +
                ", wMB=" + wMB +
                ", avgrq=" + avgrq +
                ", avgqu=" + avgqu +
                ", await=" + await +
                ", rAwait=" + rAwait +
                ", wAwait=" + wAwait +
                ", svctm=" + svctm +
                ", util=" + util +
                '}';
    }

    public void clear() {
        device = null;
        rrqm = null;
        wrqm = null;
        r = null;
        w = null;
        rMB = null;
        wMB = null;
        avgrq = null;
        avgqu = null;
        await = null;
        rAwait = null;
        wAwait = null;
        svctm = null;
        util = null;
    }

}