package com.sysmin.core.system.domain;

/**
 * @author:Li
 * @time: 2018/12/26 21:12
 * @version: 1.0.0
 * ifstat -lST
 */
public class FlowDO {

    /**
     * 网卡下行流量
     */
    private Double in;

    /**
     * 网卡上行流量
     */
    private Double out;

    public FlowDO(Double in, Double out) {
        this.in = in;
        this.out = out;
    }

    public FlowDO() {
    }

    public Double getIn() {
        return in;
    }

    public FlowDO setIn(Double in) {
        this.in = in;
        return this;
    }

    public Double getOut() {
        return out;
    }

    public FlowDO setOut(Double out) {
        this.out = out;
        return this;
    }

    @Override
    public String toString() {
        return "FlowDO{" +
                "in=" + in +
                ", out=" + out +
                '}';
    }
}
