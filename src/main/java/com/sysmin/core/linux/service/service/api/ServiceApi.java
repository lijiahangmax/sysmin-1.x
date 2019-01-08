package com.sysmin.core.linux.service.service.api;

import com.sysmin.core.linux.service.enums.ServiceType;

/**
 * @author:Li
 * @time: 2018/12/24 13:36
 * @version: 1.0.0
 * 服务api
 */
public interface ServiceApi {

    /**
     * 执行service
     *
     * @param name 服务名称
     * @param opt  执行什么操作
     * @return
     */
    String serviceRun(String name, ServiceType opt);

    /**
     * 执行service
     *
     * @param name 服务名称
     * @param opt  执行什么操作
     * @return
     */
    String serviceRun(String name, String opt);

    /**
     * 启动服务
     *
     * @param name
     * @return
     */
    String serviceStart(String name);

    /**
     * 停止服务
     *
     * @param name
     * @return
     */
    String serviceStop(String name);

    /**
     * 重启服务
     *
     * @param name
     * @return
     */
    String serviceRestart(String name);

    /**
     * 服务状态
     *
     * @param name
     * @return
     */
    String serviceStatus(String name);

    /**
     * 获得全部服务
     *
     * @return
     */
    String[] getAllService();

    /**
     * 获得服务信息
     *
     * @param name 服务
     * @return
     */
    String[] getService(String name);

    /**
     * 切换等级
     *
     * @param name  服务名称
     * @param level 0123456 等级
     * @param on    true:on  false:off
     * @return 1:成功 0:失败
     */
    int changeLevel(String name, String level, boolean on);

    /**
     * 添加服务
     *
     * @param name 服务名称
     * @return 1:成功  2:失败
     */
    int addService(String name);

    /**
     * 删除服务
     *
     * @param name 服务名称
     * @return 1:成功  2:失败
     */
    int delService(String name);

}
