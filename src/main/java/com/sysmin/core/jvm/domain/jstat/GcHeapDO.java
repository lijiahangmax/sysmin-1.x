package com.sysmin.core.jvm.domain.jstat;

/**
 * @author:Li
 * @time: 2018/12/30 18:45
 * @version: 1.0.0
 * jstat -gc pid
 */
public class GcHeapDO {

    /**
     * 年轻代中第一个幸存区的容量 （字节）
     */
    private Double s0c;

    /**
     * 年轻代中第二个幸存区的容量 （字节）
     */
    private Double s1c;

    /**
     * 年轻代中第一个幸存区已使用空间 (字节)
     */
    private Double s0u;

    /**
     * 年轻代中第二个幸存区已使用空间 (字节)
     */
    private Double s1u;

    /**
     * 年轻代中伊甸园的容量 (字节)
     */
    private Double ec;

    /**
     * 年轻代中Eden伊甸园已使用空间 (字节)
     */
    private Double eu;

    /**
     * Old代的容量 (字节)
     */
    private Double oc;

    /**
     * Old代已使用空间 (字节)
     */
    private Double ou;

    /**
     * 元空间的容量 (字节)
     */
    private Double mc;

    /**
     * 元空间已使用空间 (字节)
     */
    private Double mu;

    /**
     * 压缩类空间大小
     */
    private Double ccsc;

    /**
     * 压缩类空间使用大小
     */
    private Double ccsu;

    /**
     * 年轻代gc次数
     */
    private Integer ygc;

    /**
     * 年轻代gc消耗时间
     */
    private Double ygct;

    /**
     * 老年代gc次数
     */
    private Integer fgc;

    /**
     * 老年代gc消耗时间
     */
    private Double fgct;

    /**
     * gc消耗总时间
     */
    private Double gct;

    /**
     * 当前时间
     */
    private String date;

    public Double getS0c() {
        return s0c;
    }

    public GcHeapDO setS0c(Double s0c) {
        this.s0c = s0c;
        return this;
    }

    public Double getS1c() {
        return s1c;
    }

    public GcHeapDO setS1c(Double s1c) {
        this.s1c = s1c;
        return this;
    }

    public Double getS0u() {
        return s0u;
    }

    public GcHeapDO setS0u(Double s0u) {
        this.s0u = s0u;
        return this;
    }

    public Double getS1u() {
        return s1u;
    }

    public GcHeapDO setS1u(Double s1u) {
        this.s1u = s1u;
        return this;
    }

    public Double getEc() {
        return ec;
    }

    public GcHeapDO setEc(Double ec) {
        this.ec = ec;
        return this;
    }

    public Double getEu() {
        return eu;
    }

    public GcHeapDO setEu(Double eu) {
        this.eu = eu;
        return this;
    }

    public Double getOc() {
        return oc;
    }

    public GcHeapDO setOc(Double oc) {
        this.oc = oc;
        return this;
    }

    public Double getOu() {
        return ou;
    }

    public GcHeapDO setOu(Double ou) {
        this.ou = ou;
        return this;
    }

    public Double getMc() {
        return mc;
    }

    public GcHeapDO setMc(Double mc) {
        this.mc = mc;
        return this;
    }

    public Double getMu() {
        return mu;
    }

    public GcHeapDO setMu(Double mu) {
        this.mu = mu;
        return this;
    }

    public Double getCcsc() {
        return ccsc;
    }

    public GcHeapDO setCcsc(Double ccsc) {
        this.ccsc = ccsc;
        return this;
    }

    public Double getCcsu() {
        return ccsu;
    }

    public GcHeapDO setCcsu(Double ccsu) {
        this.ccsu = ccsu;
        return this;
    }

    public Integer getYgc() {
        return ygc;
    }

    public GcHeapDO setYgc(Integer ygc) {
        this.ygc = ygc;
        return this;
    }

    public Double getYgct() {
        return ygct;
    }

    public GcHeapDO setYgct(Double ygct) {
        this.ygct = ygct;
        return this;
    }

    public Integer getFgc() {
        return fgc;
    }

    public GcHeapDO setFgc(Integer fgc) {
        this.fgc = fgc;
        return this;
    }

    public Double getFgct() {
        return fgct;
    }

    public GcHeapDO setFgct(Double fgct) {
        this.fgct = fgct;
        return this;
    }

    public Double getGct() {
        return gct;
    }

    public GcHeapDO setGct(Double gct) {
        this.gct = gct;
        return this;
    }

    public String getDate() {
        return date;
    }

    public GcHeapDO setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "GcHeapDO{" +
                "s0c=" + s0c +
                ", s1c=" + s1c +
                ", s0u=" + s0u +
                ", s1u=" + s1u +
                ", ec=" + ec +
                ", eu=" + eu +
                ", oc=" + oc +
                ", ou=" + ou +
                ", mc=" + mc +
                ", mu=" + mu +
                ", ccsc=" + ccsc +
                ", ccsu=" + ccsu +
                ", ygc=" + ygc +
                ", ygct=" + ygct +
                ", fgc=" + fgc +
                ", fgct=" + fgct +
                ", gct=" + gct +
                ", date='" + date + '\'' +
                '}';
    }

    public GcHeapDO clear() {
        s0c = null;
        s1c = null;
        s0u = null;
        s1u = null;
        ec = null;
        eu = null;
        oc = null;
        ou = null;
        mc = null;
        mu = null;
        ccsc = null;
        ccsu = null;
        ygc = null;
        ygct = null;
        fgc = null;
        fgct = null;
        gct = null;
        date = null;
        return this;
    }

}
