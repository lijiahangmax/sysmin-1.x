package com.sysmin.core.system.service.impl;

import com.sysmin.core.linux.defult.service.impl.BaseImpl;
import com.sysmin.core.system.domain.SystemDO;
import com.sysmin.core.system.service.api.SystemApi;
import com.sysmin.global.GlobalConfig;
import com.sysmin.util.BashUtil;
import com.sysmin.util.DateUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author:Li
 * @time: 2019/1/22 21:57
 * @version: 1.0.0
 */
@Service
public class SystemServiceImpl implements SystemApi {

    @Resource
    private BaseImpl baseImpl;

    private int systemType = ((String) GlobalConfig.getValue("OSSystem")).contains("Linux") ? 1 : 0;

    @Override
    public SystemDO getSystemInfo() {
        SystemDO system = new SystemDO()
                .setIp((String) GlobalConfig.getValue("ip"));
        if (systemType == 1) {
            String[] lastTime = BashUtil.exec("who -b").split(" ");
            system.setUserName(baseImpl.users().toString())
                    .setHostName(baseImpl.hostName())
                    .setOsVersion(StringUtil.replaceBlank(BashUtil.exec(BashUtil.toLinuxCommand("lsb_release -a | grep Description")).split(":")[1], " "))
                    .setUpTime(DateUtil.dateDistance(lastTime[lastTime.length - 2] + " " + lastTime[lastTime.length - 1], "yyyy-MM-dd HH:mm", DateUtil.getNowDate(DateUtil.DEFAULT_FORMAT), DateUtil.DEFAULT_FORMAT, true));
        } else {
            Map<String, String> map = System.getenv();
            String[] lastTime = BashUtil.exec("net statistics workstation", "GBK").split("\n")[3].split(" ");
            system.setUserName(map.get("USERNAME"))
                    .setHostName(map.get("COMPUTERNAME"))
                    .setOsVersion((String) GlobalConfig.getValue("OSSystem"))
                    .setUpTime(DateUtil.dateDistance(lastTime[1] + " " + lastTime[2], "yyyy/MM/dd HH:mm:ss", DateUtil.getNowDate(DateUtil.DEFAULT_FORMAT), DateUtil.DEFAULT_FORMAT, true));
        }
        return system;
    }

    @Override
    public void reboot() {
        if (systemType == 1) {
            baseImpl.reboot();
        } else {
            BashUtil.exec("shutdown -r");
        }
    }

    @Override
    public void halt() {
        if (systemType == 1) {
            baseImpl.halt();
        } else {
            BashUtil.exec("shutdown -s -t 1");
        }
    }

}
