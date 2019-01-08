package com.sysmin.core.linux.firewall.service.impl;

import com.sysmin.core.linux.firewall.domain.FireWallDO;
import com.sysmin.core.linux.firewall.service.api.FireWallApi;
import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/19 9:33
 * @version: 1.0.0
 */
public class FireWallCentos6 implements FireWallApi {

    @Override
    public int isOpen() {
        return (BashUtil.exec("service iptables status").split("\n").length != 1) ? 1 : 0;
    }

    @Override
    public void start() {
        boolean off = isOpen() == 0;
        if (off) {
            String status = BashUtil.exec("service iptables start");
        }
    }

    @Override
    public void stop() {
        boolean open = isOpen() == 1;
        if (open) {
            String status = BashUtil.exec("service iptables stop");
        }
    }

    @Override
    public void restart() {
        String status = BashUtil.exec("service iptables restart");
    }

    @Override
    public int addNorm(Object o) {
        if (!checkValid(o)) {
            return 0;
        }
        boolean open = isOpenPort(o) == 1;
        if (!open) {
            String status = BashUtil.exec("iptables -I INPUT -p tcp --dport " + o + " -j ACCEPT");
        }
        return 1;
    }

    @Override
    public int removeNorm(Object o) {
        if (checkValid(o)) {
            String status = BashUtil.exec("iptables -D INPUT -p tcp --dport " + o + " -j ACCEPT");
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 保存规范
     */
    public void saveNorm() {
        String status = BashUtil.exec("service iptables save");
    }

    @Override
    public List<FireWallDO> getStatus() {
        List<FireWallDO> list = new ArrayList<>();
        if (isOpen() == 1) {
            String[] lines = (BashUtil.exec("service iptables status")).split("\n");
            for (int i = 3; i < lines.length; i++) {
                String[] line = StringUtil.replaceBlank(lines[i], "\r").split("\r");
                if (line.length == 8) {
                    list.add(new FireWallDO()
                            .setNum(Integer.valueOf(line[0]))
                            .setTarget(line[1])
                            .setProt(line[2])
                            .setOpt(line[3])
                            .setSource(line[4])
                            .setDestination(line[5])
                            .setExplain(line[6] + " " + line[7])
                    );
                }
            }
        }
        return list;
    }

    @Override
    public int isOpenPort(Object o) {
        if (o instanceof String) {
            return 0;
        }
        for (FireWallDO fireWall : getStatus()) {
            String[] ports = fireWall.getExplain().split(":");
            if (ports.length == 3) {
                boolean rangeHave = (Integer.valueOf(ports[1]) == o || Integer.valueOf(ports[2]) == o) || ((int) o > Integer.valueOf(ports[1]) && (int) o < Integer.valueOf(ports[2]));
                if (rangeHave) {
                    return 1;
                }
            } else if (ports.length == 2) {
                if (Integer.valueOf(ports[1]) == o) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * 永久开启防火墙 开机启动
     */
    @Override
    public void alwaysOn() {
        start();
        BashUtil.exec("chkconfig iptables on");
    }

    /**
     * 永久关闭防火墙 开机不启动
     */
    @Override
    public void alwaysOff() {
        stop();
        BashUtil.exec("chkconfig iptables off");
    }

    /**
     * 清除所有规则
     */
    public void flushAll() {
        BashUtil.exec("iptables -F");
    }
}
