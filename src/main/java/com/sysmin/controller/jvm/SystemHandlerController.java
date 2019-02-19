package com.sysmin.controller.jvm;

import com.sysmin.core.system.domain.DiskDO;
import com.sysmin.core.system.service.impl.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author:Li
 * @time: 2019/1/6 20:03
 * @version: 1.0.0
 */
@Controller
public class SystemHandlerController {

    @Resource
    private IoCpuImpl ioCpuImpl;

    @Resource
    private FlowImpl flowImpl;

    @Resource
    private MemoryImpl memoryImpl;

    @Resource
    private UpTimeImpl upTimeImpl;

    @Resource
    private DiskImpl diskImpl;

    @RequestMapping("/loadbalance")
    @ResponseBody
    public Double[] upTimeLoadBalance() {
        return upTimeImpl.getUpTime().getAverage();
    }

    @RequestMapping("/systemallstart")
    @ResponseBody
    public int systemAllStart() {
        if (MemoryImpl.systemProcess.get("free") == null) {
            new Thread(() -> {
                memoryImpl.getMemory();
            }).start();
        }
        if (FlowImpl.systemProcess.get("ifstat") == null) {
            new Thread(() -> {
                flowImpl.subscribe();
            }).start();
        }
        if (IoCpuImpl.systemProcess.get("iostat") == null) {
            new Thread(() -> {
                ioCpuImpl.getIoCpuInfo();
            }).start();
            return 1;
        }
        return 1;
    }

    @RequestMapping("/diskinfo")
    @ResponseBody
    public ArrayList<DiskDO> diskInfo() {
        return diskImpl.getDiskInfo();
    }

    @RequestMapping("/iostart")
    @ResponseBody
    public int ioStart() {
        if (IoCpuImpl.systemProcess.get("iostat") == null) {
            new Thread(() -> {
                ioCpuImpl.getIoCpuInfo();
            }).start();
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("/iostop")
    @ResponseBody
    public int ioStop() {
        int size = IoCpuImpl.systemProcess.size();
        Process process = null;
        if ((process = IoCpuImpl.systemProcess.get("iostat")) != null) {
            process.destroy();
        }
        return size;
    }

    @RequestMapping("/memorystart")
    @ResponseBody
    public int memoryStart() {
        if (MemoryImpl.systemProcess.get("free") == null) {
            new Thread(() -> {
                memoryImpl.getMemory();
            }).start();
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("/memorystop")
    @ResponseBody
    public int memoryStop() {
        int size = MemoryImpl.systemProcess.size();
        Process process = null;
        if ((process = MemoryImpl.systemProcess.get("free")) != null) {
            process.destroy();
        }
        return size;
    }

    @RequestMapping("/flowstart")
    @ResponseBody
    public int flowStart() {
        if (FlowImpl.systemProcess.get("ifstat") == null) {
            new Thread(() -> {
                flowImpl.subscribe();
            }).start();
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("/flowstop")
    @ResponseBody
    public int flowStop() {
        int size = FlowImpl.systemProcess.size();
        Process process = null;
        if ((process = FlowImpl.systemProcess.get("ifstat")) != null) {
            process.destroy();
        }
        return size;
    }

}
