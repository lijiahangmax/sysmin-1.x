package com.sysmin.core.linux.firewall.service.impl;

import com.sysmin.core.linux.firewall.domain.FireWallDO;
import com.sysmin.core.linux.firewall.service.api.FireWallApi;
import com.sysmin.util.BashUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/19 9:54
 * @version: 1.0.0
 */
public class FireWallCentos7 implements FireWallApi {

    @Override
    public int isOpen() {
        String status = BashUtil.exec("firewall-cmd --state");
        return status.contains("running") ? 1 : 0;
    }

    @Override
    public void start() {
        String status = BashUtil.exec("systemctl start firewalld.service");
    }

    @Override
    public void stop() {
        String status = BashUtil.exec("systemctl stop firewalld.service");
    }

    @Override
    public void restart() {
        String status = BashUtil.exec("systemctl restart firewalld.service");
    }

    /**
     * 重新加载 firewall-cmd
     */
    public void reload() {
        String status = BashUtil.exec("firewall-cmd --reload");
    }

    @Override
    public int addNorm(Object o) {
        return addNorm(o, true);
    }

    /**
     * 添加端口/服务/端口-端口约束
     *
     * @param o      约束
     * @param always 是否永久生效 (fireWall)
     * @return 1成功  0失败
     */
    public int addNorm(Object o, boolean always) {
        StringBuilder command = new StringBuilder();
        if (checkValid(o)) {
            command.append("firewall-cmd --add-port=" + o + "/tcp ");
        }
        if (always) {
            command.append("--permanent");
        }
        String status = BashUtil.exec(command.toString());
        return status.contains("success") ? 1 : 0;
    }

    @Override
    public int removeNorm(Object o) {
        if (checkValid(o)) {
            String status = BashUtil.exec("firewall-cmd --zone=public --remove-port=" + o + "/tcp --permanent");
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<FireWallDO> getStatus() {
        List<FireWallDO> list = new ArrayList<>();
        String[] opens = (BashUtil.exec("firewall-cmd --zone=public --list-ports")).split(" ");
        for (int i = 0; i < opens.length; i++) {
            String[] data = opens[i].split("/");
            list.add(new FireWallDO().setProt(data[1]).setExplain(data[0]));
        }
        return list;
    }

    @Override
    public int isOpenPort(Object o) {
        if (checkValid(o)) {
            String status = BashUtil.exec("firewall-cmd --query-port=" + o + "/tcp");
            return "yes".equals(status) ? 1 : 0;
        } else {
            return 0;
        }
    }

    @Override
    public void alwaysOn() {
        String status = BashUtil.exec("systemctl enable firewalld.service");
    }

    @Override
    public void alwaysOff() {
        String status = BashUtil.exec("systemctl disable firewalld.service");
    }
}
