package com.sysmin.controller.system;

import com.sysmin.global.GlobalConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:Li
 * @time: 2019/1/15 21:13
 * @version: 1.0.0
 */
@Controller
public class SystemController {

    @RequestMapping("/getsystemtype")
    @ResponseBody
    public int systemType() {
        // 1: Linux 0: Windows
        return ((String) GlobalConfig.getValue("OSSystem")).contains("Linux") ? 1 : 0;
    }

}
