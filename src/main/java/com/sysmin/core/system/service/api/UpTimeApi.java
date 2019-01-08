package com.sysmin.core.system.service.api;

import com.sysmin.core.system.domain.UpTimeDO;

/**
 * @author:Li
 * @time: 2018/12/28 15:36
 * @version: 1.0.0
 */
public interface UpTimeApi {

    /**
     * 获得当前uptime
     *
     * @return
     */
    UpTimeDO getUpTime();

}
