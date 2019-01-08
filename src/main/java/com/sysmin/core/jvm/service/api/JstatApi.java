package com.sysmin.core.jvm.service.api;

import com.sysmin.core.jvm.enums.JstatType;

/**
 * @author:Li
 * @time: 2018/12/26 21:28
 * @version: 1.0.0
 */
public interface JstatApi {

    /**
     * 获得信息
     *
     * @param id   进程id
     * @param type 信息类型
     */
    void jstat(int id, JstatType type);

    /**
     * 获得信息
     *
     * @param id   进程id
     * @param time 多长时间查询一次 (ms)
     * @param type 信息类型
     */
    void jstat(int id, int time, JstatType type);

}
