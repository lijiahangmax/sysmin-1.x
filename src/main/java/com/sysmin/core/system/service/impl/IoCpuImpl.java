package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.io.CpuInfo;
import com.sysmin.core.system.domain.io.IoDO;
import com.sysmin.core.system.domain.io.IoInfo;
import com.sysmin.core.system.service.api.IoCpuApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.DateUtil;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/27 8:56
 * @version: 1.0.0
 */
public class IoCpuImpl extends BaseContinueOut implements IoCpuApi {

    /**
     * 行计数器
     */
    private int lines = 0;

    /**
     * 0:io+cpu  1:io   2:cpu
     */
    private int type = 0;

    /**
     * 全局实体对象
     */
    private IoDO iodo = new IoDO();

    /**
     * 全局io对象
     */
    private ArrayList<IoInfo> ioList = new ArrayList<>();

    @Override
    public void getIoCpuInfo() {
        type = 0;
        super.subscribe("iostat -cdxm -t 1");
    }

    @Override
    public void getIoInfo() {
        type = 1;
        super.subscribe("iostat -dxm -t 1");
    }

    @Override
    public void getCpuInfo() {
        type = 2;
        super.subscribe("iostat -c -t 1");
    }

    @Override
    protected void invoke(String data, int pid, String command) {
        if (type == 0) {
            ioCpuMonitor(data);
        } else if (type == 1) {
            ioMonitor(data);
        } else if (type == 2) {
            cpuMonitor(data);
        }
    }

    /**
     * io cpu监控
     *
     * @param data
     */
    private void ioCpuMonitor(String data) {
        lines++;
        if (lines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data))) {
            lines--;
        } else if (lines == 3) {
            iodo.setCpu(setCpuInfo(new StringTokenizer(StringUtil.replaceBlank(data, " "), " ")));
        } else if (lines > 4) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                iodo.setIo(ioList);
                System.out.println(JsonUtil.objToJson(iodo));
                lines = 1;
                iodo.getCpu().clear();
                iodo.getIo().clear();
                iodo.clear();
            } else {
                ioList.add(setIoInfo(new StringTokenizer(StringUtil.replaceBlank(data, " "), " ")));
            }
        }
    }

    /**
     * io监控
     *
     * @param data
     */
    private void ioMonitor(String data) {
        lines++;
        if (lines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data) || data.contains(DateUtil.getNowDate("yyyy")))) {
            lines--;
        } else if (lines > 1) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                iodo.setIo(ioList);
                System.out.println(JsonUtil.objToJson(iodo));
                lines = 0;
                iodo.getIo().clear();
                iodo.clear();
            } else {
                ioList.add(setIoInfo(new StringTokenizer(StringUtil.replaceBlank(data, " "), " ")));
            }
        }
    }

    /**
     * cpu监控
     *
     * @param data
     */
    private void cpuMonitor(String data) {
        lines++;
        if (lines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data) || data.contains(DateUtil.getNowDate("yyyy")))) {
            lines--;
        } else if (lines > 1) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                System.out.println(JsonUtil.objToJson(iodo.getCpu()));
                lines = 0;
                iodo.getCpu().clear();
                iodo.clear();
            } else {
                iodo.setCpu(setCpuInfo(new StringTokenizer(StringUtil.replaceBlank(data, " "), " ")));
            }
        }
    }

    /**
     * 将数据写入cpuinfo中
     *
     * @param token
     * @return
     */
    private CpuInfo setCpuInfo(StringTokenizer token) {
        return new CpuInfo()
                .setUser(Double.valueOf(token.nextToken()))
                .setNice(Double.valueOf(token.nextToken()))
                .setSystem(Double.valueOf(token.nextToken()))
                .setIowait(Double.valueOf(token.nextToken()))
                .setSteal(Double.valueOf(token.nextToken()))
                .setIdle(Double.valueOf(token.nextToken()));
    }

    /**
     * 将数据写入ioinfo中
     *
     * @param token
     * @return
     */
    private IoInfo setIoInfo(StringTokenizer token) {
        return new IoInfo()
                .setDevice(token.nextToken())
                .setRrqm(Double.valueOf(token.nextToken()))
                .setWrqm(Double.valueOf(token.nextToken()))
                .setR(Double.valueOf(token.nextToken()))
                .setW(Double.valueOf(token.nextToken()))
                .setrMB(Double.valueOf(token.nextToken()))
                .setwMB(Double.valueOf(token.nextToken()))
                .setAvgrq(Double.valueOf(token.nextToken()))
                .setAvgqu(Double.valueOf(token.nextToken()))
                .setAwait(Double.valueOf(token.nextToken()))
                .setrAwait(Double.valueOf(token.nextToken()))
                .setwAwait(Double.valueOf(token.nextToken()))
                .setSvctm(Double.valueOf(token.nextToken()))
                .setUtil(Double.valueOf(token.nextToken()));
    }

    @Override
    protected void error(String data) {
        System.out.println("error: " + data);
    }

    @Override
    protected void destroy() {
        System.out.println("destroy: 销毁");
    }

}
