package com.sysmin.core.linux.firewall.factory;

import com.sysmin.core.linux.firewall.service.api.FireWallApi;
import com.sysmin.core.linux.firewall.service.impl.FireWallCentos7;

/**
 * @author:Li
 * @time: 2018/12/19 9:31
 * @version: 1.0.0
 */
public class FireWallCentos7Factory implements FireWallFactory {
    @Override
    public FireWallApi getInstance() {
        return new FireWallCentos7();
    }
}
