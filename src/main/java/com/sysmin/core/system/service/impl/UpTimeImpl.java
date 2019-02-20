package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.UpTimeDO;
import com.sysmin.core.system.service.api.UpTimeApi;
import com.sysmin.util.BashUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
        int len = data.length;
        return new UpTimeDO().setSystemTime(data[1])
                .setUpTime(data[3])
                .setUsers(Integer.valueOf(data[len - 8]))
                .setAverage(new Double[]{
                        Double.valueOf(data[len - 3].substring(0, data[len - 3].length() - 1)),
                        Double.valueOf(data[len - 2].substring(0, data[len - 2].length() - 1)),
                        Double.valueOf(data[len - 1])
                });
    }

    @Deprecated
    public UpTimeDO getUpTimeOld() {
        String line = BashUtil.exec("uptime", true);
        String[] data = line.split(" ");
        if (data.length == 13) {
            return new UpTimeDO().setSystemTime(data[1])
                    .setUpTime(data[3])
                    .setUsers(Integer.valueOf(data[5]))
                    .setAverage(new Double[]{
                            Double.valueOf(data[10].substring(0, data[10].length() - 1)),
                            Double.valueOf(data[11].substring(0, data[11].length() - 1)),
                            Double.valueOf(data[12])
                    });
        } else if (data.length == 14) {
            return new UpTimeDO().setSystemTime(data[1])
                    .setUpTime(data[4])
                    .setUsers(Integer.valueOf(data[6]))
                    .setAverage(new Double[]{
                            Double.valueOf(data[11].substring(0, data[11].length() - 1)),
                            Double.valueOf(data[12].substring(0, data[12].length() - 1)),
                            Double.valueOf(data[13])
                    });
        } else if (data.length == 15) {
            return new UpTimeDO().setSystemTime(data[1])
                    .setUpTime(data[5])
                    .setUsers(Integer.valueOf(data[7]))
                    .setAverage(new Double[]{
                            Double.valueOf(data[12].substring(0, data[12].length() - 1)),
                            Double.valueOf(data[13].substring(0, data[13].length() - 1)),
                            Double.valueOf(data[14])
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
