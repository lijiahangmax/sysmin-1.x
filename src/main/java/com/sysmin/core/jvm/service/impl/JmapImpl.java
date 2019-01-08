package com.sysmin.core.jvm.service.impl;

import com.sysmin.core.jvm.enums.JmapType;
import com.sysmin.core.jvm.service.api.JmapApi;
import com.sysmin.global.GlobalConfig;
import com.sysmin.util.BashUtil;
import com.sysmin.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author:Li
 * @time: 2019/1/2 15:15
 * @version: 1.0.0
 */
@Service
public class JmapImpl implements JmapApi {

    @Override
    public String saveHeap(int id, JmapType type) {
        String[] data = type.getTypeToFile(id);
        if (((String) GlobalConfig.getValue("OSSystem")).contains("Linux")) {
            BashUtil.exec(BashUtil.toLinuxCommand(data[0]));
        } else {
            if (!"0".equals(data[2])) {
                FileUtil.saveDataToFile(BashUtil.exec(data[0]), data[1]);
            } else {
                BashUtil.exec(data[0]);
            }
        }
        return data[1];
    }

    @Override
    public String[] listHeapFile() {
        return FileUtil.listFile((String) GlobalConfig.getValue("dumpPath") + File.separatorChar + "heap");
    }

}
