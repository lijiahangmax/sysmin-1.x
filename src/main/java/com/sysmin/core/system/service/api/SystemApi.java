package com.sysmin.core.system.service.api;

import com.sysmin.core.system.domain.SystemDO;

/**
 * @author:Li
 * @time: 2019/1/22 21:50
 * @version: 1.0.0
 */
public interface SystemApi {

    /**
     * 获得系统信息
     *
     * @return 系统信息
     */
    SystemDO getSystemInfo();

    /**
     * 关机
     */
    void halt();

    /**
     * 重启
     */
    void reboot();

}
