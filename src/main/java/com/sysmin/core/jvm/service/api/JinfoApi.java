package com.sysmin.core.jvm.service.api;

import com.sysmin.core.jvm.enums.JinfoType;

import java.util.List;
import java.util.Map;

/**
 * @author:Li
 * @time: 2018/12/30 21:00
 * @version: 1.0.0
 */
public interface JinfoApi {

    /**
     * 获得信息
     *
     * @param id   进程id
     * @param type 信息类型
     * @return 参数信息
     */
    Map<String, List> getInfo(int id, JinfoType type);

}
