package com.sysmin.controller.jvm;

import com.sysmin.core.jvm.enums.JstatType;
import com.sysmin.core.jvm.service.impl.JstatImpl;
import com.sysmin.core.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2019/1/6 20:03
 * @version: 1.0.0
 */
@Controller
public class JvmHandlerController {

    @Resource
    private JstatImpl jstatImpl;

    @RequestMapping("/stopallmonitor")
    @ResponseBody
    public int stopAllmonitor() {
        Log.getLog("test", "stopallmonitor", "all");
        JstatImpl.jvmProcess.forEach((p) -> {
            p.destroy();
        });
        return 1;
    }

    @RequestMapping("/monitorallopt")
    @ResponseBody
    public int monitorAllOpt(String pids) {
        StringTokenizer token = new StringTokenizer(pids, ",");
        while (token.hasMoreTokens()) {
            int pid = Integer.valueOf(token.nextToken());
            new Thread(() -> {
                jstatImpl.jstat(pid, JstatType.CLASS);
            }).start();
        }
        return 1;
    }

}
