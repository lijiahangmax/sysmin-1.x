package com.sysmin.core.linux.net.service.api;

import com.sysmin.core.linux.net.domain.NetInfoDO;

import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/21 19:54
 * @version: 1.0.0
 */
public interface NetInfoApi {

    /**
     * 获得所有网络信息
     *
     * @return 网络信息
     */
    List<NetInfoDO> getAllNetInfo();

    /**
     * 获得指定进程的网络信息
     *
     * @param process 进程
     * @return 网络信息
     */
    List<NetInfoDO> getAllNetInfo(String process);

    /**
     * 获得所有tcp网络信息
     *
     * @return tcp信息
     */
    List<NetInfoDO> getTcpNetInfo();

    /**
     * 获得所有udp网络信息
     *
     * @return udp信息
     */
    List<NetInfoDO> getUdpNetInfo();

}
