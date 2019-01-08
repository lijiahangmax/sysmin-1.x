package com.sysmin.core.linux.net.domain;

/**
 * @author:Li
 * @time: 2018/12/21 19:45
 * @version: 1.0.0
 */
public class NetInfoDO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 协议类型
     */
    private String proto;

    /**
     * 接收总量
     */
    private String recv;

    /**
     * 发送总量
     */
    private String send;

    /**
     * 本机地址
     */
    private String localAddress;

    /**
     * 地址
     */
    private String foreignAddress;

    /**
     * 状态
     */
    private String state;

    /**
     * 程序名称
     */
    private String name;

    public Integer getPid() {
        return pid;
    }

    public NetInfoDO setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public String getProto() {
        return proto;
    }

    public NetInfoDO setProto(String proto) {
        this.proto = proto;
        return this;
    }

    public String getRecv() {
        return recv;
    }

    public NetInfoDO setRecv(String recv) {
        this.recv = recv;
        return this;
    }

    public String getSend() {
        return send;
    }

    public NetInfoDO setSend(String send) {
        this.send = send;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public NetInfoDO setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public String getForeignAddress() {
        return foreignAddress;
    }

    public NetInfoDO setForeignAddress(String foreignAddress) {
        this.foreignAddress = foreignAddress;
        return this;
    }

    public String getState() {
        return state;
    }

    public NetInfoDO setState(String state) {
        this.state = state;
        return this;
    }

    public String getName() {
        return name;
    }

    public NetInfoDO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return proto + "\t" + pid + "\t" + recv + "\t" + send + "\t" + localAddress + "\t" + foreignAddress + "\t" + state + "\t" + name;
    }
}
