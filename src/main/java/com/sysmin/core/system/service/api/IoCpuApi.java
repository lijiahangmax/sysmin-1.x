package com.sysmin.core.system.service.api;

/**
 * @author:Li
 * @time: 2018/12/27 20:06
 * @version: 1.0.0
 * io和cpu监控
 */
public interface IoCpuApi {

    /**
     * 获得当前io信息
     */
    void getIoInfo();

    /**
     * 获得当前cpu信息
     */
    void getCpuInfo();

    /**
     * 获得当前io和cpu信息
     */
    void getIoCpuInfo();

    /**
     * 安装iostat
     * yum -y install sysstat
     *
     * @return 0安装失败 1安装成功
     */
    int install();

}
