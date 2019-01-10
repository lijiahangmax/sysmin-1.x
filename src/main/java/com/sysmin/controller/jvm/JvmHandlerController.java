package com.sysmin.controller.jvm;

import com.sysmin.core.jvm.domain.JstackDO;
import com.sysmin.core.jvm.enums.JstackType;
import com.sysmin.core.jvm.enums.JstatType;
import com.sysmin.core.jvm.service.impl.JstackImpl;
import com.sysmin.core.jvm.service.impl.JstatImpl;
import com.sysmin.core.log.Log;
import com.sysmin.global.LayuiTableVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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

    @Resource
    private JstackImpl jstackImpl;

    @RequestMapping("/stopallmonitor")
    @ResponseBody
    public int stopAllMonitor() {
        Log.getLog("test", "stopallmonitor", "all");
        int size = JstatImpl.jvmProcess.size();
        JstatImpl.jvmProcess.forEach((p) -> {
            p.destroy();
        });
        JstatImpl.jvmProcess.clear();
        return size;
    }

    @RequestMapping("/monitorallopt")
    @ResponseBody
    public int monitorAllOpt(String pids) {
        try {
            StringTokenizer token = new StringTokenizer(pids, ",");
            while (token.hasMoreTokens()) {
                Log.getLog("test", "startallmonitor", "all");
                int pid = Integer.valueOf(token.nextToken());
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.CLASS);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.GC);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.GCUTIL);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.COMPILER);
                }).start();
                new Thread(() -> {
                    jstatImpl.jstat(pid, JstatType.PRINTCOMPILATION);
                }).start();
            }
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    @RequestMapping("/monitorallstack")
    @ResponseBody
    public Map<Integer, JstackDO> monitorAllStack(String pids) {
        Map<Integer, JstackDO> map = new HashMap<>();
        StringTokenizer token = new StringTokenizer(pids, ",");
        while (token.hasMoreTokens()) {
            int pid = Integer.valueOf(token.nextToken());
            JstackDO jstack = jstackImpl.jstack(pid, JstackType.DEFAULT);
            map.put(pid, jstack);
        }
        return map;
    }

    @RequestMapping("/monitorsize")
    @ResponseBody
    public int getMonitorSize() {
        return JstatImpl.jvmProcess.size();
    }

    @RequestMapping("/createthreadsnap")
    @ResponseBody
    public String createThreadSnap(int pid) {
        Log.getLog("test", "createThreadSnap " + pid, "all");
        return jstackImpl.threadSnap(pid, JstackType.DEFAULT);
    }

    @RequestMapping("/getthreadsnap")
    @ResponseBody
    public LayuiTableVO getThreadSnap(int page, int limit) {
        return jstackImpl.getThreadSnap(page, limit);
    }

}
