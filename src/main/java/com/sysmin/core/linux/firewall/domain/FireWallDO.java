package com.sysmin.core.linux.firewall.domain;

/**
 * @author:Li
 * @time: 2018/12/19 10:10
 * @version: 1.0.0
 * iptables/firewall实体类
 */
public class FireWallDO {

    private Integer num;
    private String target;
    private String prot;
    private String opt;
    private String source;
    private String destination;
    private String explain;

    public Integer getNum() {
        return num;
    }

    public FireWallDO setNum(Integer num) {
        this.num = num;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public FireWallDO setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getProt() {
        return prot;
    }

    public FireWallDO setProt(String prot) {
        this.prot = prot;
        return this;
    }

    public String getOpt() {
        return opt;
    }

    public FireWallDO setOpt(String opt) {
        this.opt = opt;
        return this;
    }

    public String getSource() {
        return source;
    }

    public FireWallDO setSource(String source) {
        this.source = source;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public FireWallDO setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getExplain() {
        return explain;
    }

    public FireWallDO setExplain(String explain) {
        this.explain = explain;
        return this;
    }

    @Override
    public String toString() {
        return num + "\t" + target + "\t" + prot + "\t" + opt + "\t" + source + "\t" + destination + "\t" + explain;
    }
}
