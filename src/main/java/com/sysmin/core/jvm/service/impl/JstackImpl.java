package com.sysmin.core.jvm.service.impl;

import com.sysmin.core.jvm.domain.JstackDO;
import com.sysmin.core.jvm.enums.JstackType;
import com.sysmin.core.jvm.service.api.JstackApi;
import com.sysmin.global.GlobalConfig;
import com.sysmin.util.BashUtil;
import com.sysmin.util.FileUtil;
import com.sysmin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author:Li
 * @time: 2019/1/1 11:04
 * @version: 1.0.0
 */
@Service
public class JstackImpl implements JstackApi {

    /**
     * 线程前缀
     */
    private final static String PREFIX = "java.lang.Thread.State: ";

    @Override
    public JstackDO jstack(int id, JstackType type) {
        String data = "";
        data = BashUtil.exec(type.getType(id));
        return new JstackDO()
                .setPid(id)
                .setTotal(StringUtil.findNum(data, "nid"))
                .setRunning(StringUtil.findNum(data, PREFIX + "RUNNABLE"))
                .setWaiting(StringUtil.findNum(data, PREFIX + "WAITING"))
                .setTimeWaiting(StringUtil.findNum(data, PREFIX + "TIMED_WAITING"))
                .setVmToatl();
    }

    @Override
    public String threadSnap(int id, JstackType type) {
        String path = FileUtil.checkPath(id, "thread", "thread_", ".txt");
        String command = type.getType(id, path);
        if (((String) GlobalConfig.getValue("OSSystem")).contains("Linux")) {
            BashUtil.exec(BashUtil.toLinuxCommand(command));
        } else {
            int result = FileUtil.saveDataToFile(BashUtil.exec(command), path);
            if (result != 1) {
                return null;
            }
        }
        return path;
    }

    @Override
    public String[] listThreadSnap() {
        return FileUtil.listFile((String) GlobalConfig.getValue("dumpPath") + File.separatorChar + "thread");
    }

}
