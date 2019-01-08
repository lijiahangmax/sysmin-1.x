package com.sysmin.core.linux.service.service.impl;

import com.sysmin.core.linux.service.enums.ServiceType;
import com.sysmin.core.linux.service.service.api.ServiceApi;
import com.sysmin.util.BashUtil;
import org.springframework.stereotype.Service;

/**
 * @author:Li
 * @time: 2018/12/24 14:04
 * @version: 1.0.0
 */
@Service
public class ServiceImpl implements ServiceApi {

    @Override
    public String serviceRun(String name, ServiceType opt) {
        return serviceExec(name, opt.getType());
    }

    @Override
    public String serviceRun(String name, String opt) {
        return serviceExec(name, opt);
    }

    @Override
    public String serviceStart(String name) {
        return serviceExec(name, "start");
    }

    @Override
    public String serviceStop(String name) {
        return serviceExec(name, "stop");
    }

    @Override
    public String serviceRestart(String name) {
        return serviceExec(name, "restart");
    }

    @Override
    public String serviceStatus(String name) {
        return serviceExec(name, "status");
    }

    /**
     * 服务的执行类
     *
     * @param name 服务名称
     * @param args 服务执行
     * @return
     */
    private String serviceExec(String name, String args) {
        return BashUtil.exec("service " + name + " " + args, true);
    }

    @Override
    public String[] getAllService() {
        return (BashUtil.exec("chkconfig --list", true)).split("\n");
    }

    @Override
    public String[] getService(String name) {
        return (BashUtil.exec("chkconfig --list " + name, true)).split("\n");
    }

    @Override
    public int changeLevel(String name, String level, boolean on) {
        String status = BashUtil.exec("chkconfig --level " + level + " " + name + " " + (on ? "on" : "off"), true);
        return "".equals(status) ? 1 : 0;
    }

    @Override
    public int addService(String name) {
        String status = BashUtil.exec("chkconfig --add " + name, true);
        return "".equals(status) ? 1 : 0;
    }

    @Override
    public int delService(String name) {
        String status = BashUtil.exec("chkconfig --del " + name, true);
        return "".equals(status) ? 1 : 0;
    }
}
