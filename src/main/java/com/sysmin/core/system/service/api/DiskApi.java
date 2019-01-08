package com.sysmin.core.system.service.api;

import com.sysmin.core.system.domain.DiskDO;

import java.util.ArrayList;

/**
 * @author:Li
 * @time: 2018/12/27 8:55
 * @version: 1.0.0
 */
public interface DiskApi {

    /**
     * 获得硬盘的信息
     *
     * @return
     */
    ArrayList<DiskDO> getDiskInfo();

}
