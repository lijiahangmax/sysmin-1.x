package com.sysmin.core.jvm.service.api;

import com.sysmin.core.jvm.domain.JstackDO;
import com.sysmin.core.jvm.enums.JstackType;

/**
 * @author:Li
 * @time: 2018/12/30 21:09
 * @version: 1.0.0
 */
public interface JstackApi {

    /**
     * 获得信息
     *
     * @param id   进程id
     * @param type 信息类型
     * @return 线程信息
     */
    JstackDO jstack(int id, JstackType type);

    /**
     * 转储线程快照到本地
     *
     * @param id   进程id
     * @param type 信息类型
     * @return 文件路径
     */
    String threadSnap(int id, JstackType type);

    /**
     * 获得线程快照列表
     *
     * @return
     */
    String[] listThreadSnap();

}
