package com.sysmin.core.jvm.domain.jstat;

/**
 * @author:Li
 * @time: 2018/12/30 18:44
 * @version: 1.0.0
 * jstat -gcutil pid
 */
public class GcUtilDO {

    /**
     * 年轻代中幸存1区当前使用比例
     */
    private Double s0;

    /**
     * 年轻代中幸存2区当前使用比例
     */
    private Double s1;

    /**
     * 年轻代中伊甸园区使用比例
     */
    private Double e;

    /**
     * 老年代使用比例
     */
    private Double o;

    /**
     * 元数据区使用比例
     */
    private Double m;

    /**
     * 压缩使用比例
     */
    private Double ccs;

    /**
     * 年轻代gc次数
     */
    private Integer ygc;

    /**
     * 年轻代中gc所用时间(s)
     */
    private Double ygct;

    /**
     * 老年代gc次数
     */
    private Integer fgc;

    /**
     * 老年代垃gc所用时间(s)
     */
    private Double fgct;

    /**
     * gc用的总时间(s)
     */
    private Double gct;

    public Double getS0() {
        return s0;
    }

    public GcUtilDO setS0(Double s0) {
        this.s0 = s0;
        return this;
    }

    public Double getS1() {
        return s1;
    }

    public GcUtilDO setS1(Double s1) {
        this.s1 = s1;
        return this;
    }

    public Double getE() {
        return e;
    }

    public GcUtilDO setE(Double e) {
        this.e = e;
        return this;
    }

    public Double getO() {
        return o;
    }

    public GcUtilDO setO(Double o) {
        this.o = o;
        return this;
    }

    public Double getM() {
        return m;
    }

    public GcUtilDO setM(Double m) {
        this.m = m;
        return this;
    }

    public Double getCcs() {
        return ccs;
    }

    public GcUtilDO setCcs(Double ccs) {
        this.ccs = ccs;
        return this;
    }

    public Integer getYgc() {
        return ygc;
    }

    public GcUtilDO setYgc(Integer ygc) {
        this.ygc = ygc;
        return this;
    }

    public Double getYgct() {
        return ygct;
    }

    public GcUtilDO setYgct(Double ygct) {
        this.ygct = ygct;
        return this;
    }

    public Integer getFgc() {
        return fgc;
    }

    public GcUtilDO setFgc(Integer fgc) {
        this.fgc = fgc;
        return this;
    }

    public Double getFgct() {
        return fgct;
    }

    public GcUtilDO setFgct(Double fgct) {
        this.fgct = fgct;
        return this;
    }

    public Double getGct() {
        return gct;
    }

    public GcUtilDO setGct(Double gct) {
        this.gct = gct;
        return this;
    }

    @Override
    public String toString() {
        return "GcUtilDO{" +
                "s0=" + s0 +
                ", s1=" + s1 +
                ", e=" + e +
                ", o=" + o +
                ", m=" + m +
                ", ccs=" + ccs +
                ", ygc=" + ygc +
                ", ygct=" + ygct +
                ", fgc=" + fgc +
                ", fgct=" + fgct +
                ", gct=" + gct +
                '}';
    }

    public GcUtilDO clear() {
        s0 = null;
        s1 = null;
        e = null;
        o = null;
        m = null;
        ccs = null;
        ygc = null;
        ygct = null;
        fgc = null;
        fgct = null;
        gct = null;
        return this;
    }

}
