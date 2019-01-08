package com.sysmin.core.system.service.api;

/**
 * @author:Li
 * @time: 2018/12/27 14:45
 * @version: 1.0.0
 * 监控流量 必须下载ifstat
 */
public interface FlowApi {

    /**
     * 订阅所有网卡信息
     */
    void subscribe();

    /**
     * 订阅一个网卡信息
     *
     * @param name 网卡名称
     */
    void subscribe(String name);

}
