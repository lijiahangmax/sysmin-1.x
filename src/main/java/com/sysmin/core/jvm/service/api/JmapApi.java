package com.sysmin.core.jvm.service.api;

import com.sysmin.core.jvm.enums.JmapType;

import java.io.IOException;

/**
 * @author:Li
 * @time: 2018/12/26 21:29
 * @version: 1.0.0
 */
public interface JmapApi {

    /**
     * 保存堆信息
     *
     * @param id   进程id
     * @param type 类型
     * @return 文件路径
     */
    String saveHeap(int id, JmapType type) throws IOException;

    /**
     * 获取所有jmap保存的文件
     *
     * @return 文件信息
     */
    String[] listHeapFile();

}
