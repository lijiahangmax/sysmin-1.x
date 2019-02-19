package com.sysmin.core.system.service.impl;

import com.sysmin.core.system.domain.io.CpuInfo;
import com.sysmin.core.system.domain.io.IoDO;
import com.sysmin.core.system.domain.io.IoInfo;
import com.sysmin.core.system.service.api.IoCpuApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.DateUtil;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/27 8:56
 * @version: 1.0.0
 */
@Service
@Scope("prototype")
public class IoCpuImpl extends BaseContinueOut implements IoCpuApi {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 行计数器
     */
    private int cpuLines = 0;
    private int ioLines = 0;
    private int ioCpuLines = 0;

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

    /**
     * 结果数据集
     */
    private Map<String, Map<String, Object>> res = new HashMap<>(13);

    @Override
    public void getIoCpuInfo() {
        type = 0;
        super.subscribe("iostat -cdxm -t 1", true);
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
        ioCpuLines++;
        if (ioCpuLines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data))) {
            ioCpuLines--;
        } else if (ioCpuLines == 3) {
            iodo.setCpu(setCpuInfo(new StringTokenizer(StringUtil.replaceBlank(data, " "), " ")));
        } else if (ioCpuLines > 4) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                iodo.setIo(ioList);
                simpMessagingTemplate.convertAndSendToUser("test", "/iocpu", iodo);
                // System.out.println(JsonUtil.objToJson(iodo));
                ioCpuLines = 1;
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
        ioLines++;
        if (ioLines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data) || data.contains(DateUtil.getNowDate("yyyy")))) {
            ioLines--;
        } else if (ioLines > 1) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                iodo.setIo(ioList);
                System.out.println(JsonUtil.objToJson(iodo));
                ioLines = 0;
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
        cpuLines++;
        if (cpuLines == 1 && (data.contains("Linux") || StringUtil.checkEmpty(data) || data.contains(DateUtil.getNowDate("yyyy")))) {
            cpuLines--;
        } else if (cpuLines > 1) {
            if (data.contains(DateUtil.getNowDate("yyyy"))) {
                System.out.println(JsonUtil.objToJson(iodo.getCpu()));
                cpuLines = 0;
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
        if (data.contains("command not found")) {
            System.out.println("未安装 iostat");
        } else {
            System.out.println("error: " + data);
        }
        System.out.println("error: " + data);
    }

    @Override
    protected void destroy() {
        cpuLines = 0;
        ioLines = 0;
        ioCpuLines = 0;
        System.out.println("destroy: 销毁");
    }

}
