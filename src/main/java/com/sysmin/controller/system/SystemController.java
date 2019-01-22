package com.sysmin.controller.system;

import com.sysmin.core.log.Log;
import com.sysmin.core.system.domain.SystemDO;
import com.sysmin.core.system.service.impl.SystemServiceImpl;
import com.sysmin.global.GlobalConfig;
import com.sysmin.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author:Li
 * @time: 2019/1/15 21:13
 * @version: 1.0.0
 */
@Controller
public class SystemController {

    @Resource
    private SystemServiceImpl systemServiceImpl;

    @RequestMapping("/getsystemtype")
    @ResponseBody
    public int systemType() {
        // 1: Linux 0: Windows
        return ((String) GlobalConfig.getValue("OSSystem")).contains("Linux") ? 1 : 0;
    }

    @RequestMapping("/getsysteminfo")
    @ResponseBody
    public SystemDO systemInfo() {
        return systemServiceImpl.getSystemInfo();
    }

    @RequestMapping("/halt")
    public String halt() {
        Log.getLog("test", DateUtil.getNowDate(DateUtil.DEFAULT_FORMAT) + " halt", "halt");
        systemServiceImpl.halt();
        return "index";
    }

    @RequestMapping("/reboot")
    public String reboot() {
        Log.getLog("test", DateUtil.getNowDate(DateUtil.DEFAULT_FORMAT) + " reboot", "reboot");
        systemServiceImpl.reboot();
        return "index";
    }

}
