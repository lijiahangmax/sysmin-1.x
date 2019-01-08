package com.sysmin.core.linux.process.service.api;

import com.sysmin.core.linux.process.domain.ProcessDO;
import com.sysmin.core.linux.process.enums.KillType;

import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/21 14:22
 * @version: 1.0.0
 */
public interface ProcessApi {

    /**
     * 获得所有进程信息
     *
     * @return
     */
    List<ProcessDO> getProcess();

    /**
     * 获得进程信息
     *
     * @param process
     * @return
     */
    List<ProcessDO> getProcess(String process);

    /**
     * 杀死进程 默认 -9
     *
     * @param pid
     */
    void killProcess(int pid);

    /**
     * 杀死进程
     *
     * @param pid
     * @param sign kill级别
     */
    void killProcess(int pid, KillType sign);

    /**
     * 暂停进程
     *
     * @param pid
     */
    void stopProcess(int pid);


    /**
     * 启动进程
     *
     * @param pid
     */
    void startProcess(int pid);

}
