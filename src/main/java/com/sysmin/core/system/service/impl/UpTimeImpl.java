package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.UpTimeDO;
import com.sysmin.core.system.service.api.UpTimeApi;
import com.sysmin.util.BashUtil;
import org.springframework.stereotype.Service;

/**
 * @author:Li
 * @time: 2018/12/28 15:37
 * @version: 1.0.0
 */
@Service
public class UpTimeImpl implements UpTimeApi {
    @Override
    public UpTimeDO getUpTime() {
        String line = BashUtil.exec("uptime", true);
        String[] data = line.split(" ");
        if (data.length == 14) {
            return new UpTimeDO().setSystemTime(data[1])
                    .setUpTime(data[4])
                    .setUsers(Integer.valueOf(data[6]))
                    .setAverage(new Double[]{
                            Double.valueOf(data[11].substring(0, data[11].length() - 1)),
                            Double.valueOf(data[12].substring(0, data[12].length() - 1)),
                            Double.valueOf(data[13])
                    });
            // } else if (data.length == 16) {
        } else {
            return new UpTimeDO().setSystemTime(data[1])
                    .setUpTime(data[3] + " day " + data[6].substring(0, data[6].length() - 1))
                    .setUsers(Integer.valueOf(data[8]))
                    .setAverage(new Double[]{
                            Double.valueOf(data[13].substring(0, data[13].length() - 1)),
                            Double.valueOf(data[14].substring(0, data[14].length() - 1)),
                            Double.valueOf(data[15])
                    });
        }
    }
}
