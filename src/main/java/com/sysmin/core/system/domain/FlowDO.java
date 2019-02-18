package com.sysmin.core.system.domain;

import com.sysmin.util.DateUtil;

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

    /**
     * 当前时间
     */
    private String date;

    public FlowDO(Double in, Double out) {
        this.in = in;
        this.out = out;
        this.date = DateUtil.getNowDate("mm:ss");
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

    public String getDate() {
        return date;
    }

    public void setDate() {
        date = DateUtil.getNowDate("mm:ss");
    }

    @Override
    public String toString() {
        return "FlowDO{" +
                "in=" + in +
                ", out=" + out +
                ", date='" + date + '\'' +
                '}';
    }
}
