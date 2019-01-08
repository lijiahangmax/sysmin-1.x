package com.sysmin.core.linux.firewall.factory;

import com.sysmin.core.linux.firewall.service.api.FireWallApi;

/**
 * @author:Li
 * @time: 2018/12/19 9:25
 * @version: 1.0.0
 */
public interface FireWallFactory {

    /**
     * 创建实例
     *
     * @return
     */
    FireWallApi getInstance();

}
