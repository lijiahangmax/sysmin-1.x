package com.sysmin.core.linux.defult.service.api;

import com.sysmin.core.linux.defult.domain.LastLogDO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author:Li
 * @time: 2018/12/25 13:36
 * @version: 1.0.0
 */
public interface BaseApi {

    /**
     * 修改权限
     *
     * @param file 文件
     * @param mod  权限
     * @return 1:成功 0:失败
     * @throws FileNotFoundException 文件不存在
     */
    int chmod(String file, int mod) throws FileNotFoundException;

    /**
     * 获得进程pid
     *
     * @param name 进程
     * @return
     */
    ArrayList<Integer> pidOf(String name);

    /**
     * ping 默认3次
     *
     * @param host 主机
     * @return
     */
    ArrayList<String> ping(String host);

    /**
     * ping
     *
     * @param host 主机
     * @param time 次数
     * @return
     */
    ArrayList<String> ping(String host, int time);

    /**
     * 从根目录查找文件
     *
     * @param file 文件名
     * @return
     */
    ArrayList<String> find(String file);

    /**
     * 从指定目录查找文件
     *
     * @param file 文件名
     * @param dir  目录
     * @return
     */
    ArrayList<String> find(String file, String dir);

    /**
     * 重启
     */
    void reboot();

    /**
     * 关机
     *
     * @param time 定时关机时间
     * @param type true关机 false重启
     */
    void shutDown(String time, boolean type);

    /**
     * 取消重启
     */
    void shutDownCancel();

    /**
     * 关机
     */
    void halt();

    /**
     * 获得系统的用户信息
     *
     * @return 0:名称  1:线路  2:时间  3:备注
     */
    String[] who();

    /**
     * 当前登录的用户
     *
     * @return
     */
    ArrayList<String> users();

    /**
     * 查询最后登录信息
     *
     * @param user 用户
     * @return
     */
    LastLogDO getLastLog(String user);

    /**
     * 查看所有用户登录信息
     *
     * @return
     */
    ArrayList<LastLogDO> getAllLastLog();

}
