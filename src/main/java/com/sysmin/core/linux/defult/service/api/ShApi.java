package com.sysmin.core.linux.defult.service.api;

import java.io.FileNotFoundException;

/**
 * @author:Li
 * @time: 2018/12/24 15:54
 * @version: 1.0.0
 */
public interface ShApi {

    String HEAD = "#!/bin/bash";

    /**
     * 执行sh文件
     *
     * @param file sh文件 默认不等待
     * @return 结果
     * @throws FileNotFoundException 文件不存在
     */
    String run(String file) throws FileNotFoundException;

    /**
     * 执行sh文件
     *
     * @param file sh文件
     * @param wait 是否等待执行
     * @return 结果
     * @throws FileNotFoundException 文件不存在
     */
    String run(String file, boolean wait) throws FileNotFoundException;

    /**
     * 执行sh文件
     *
     * @param file  sh文件
     * @param wait  是否等待执行
     * @param param 运行参数
     * @return 结果
     * @throws FileNotFoundException 文件不存在
     */
    String run(String file, boolean wait, String param) throws FileNotFoundException;

    /**
     * 向脚本写入信息
     *
     * @param file 脚本文件名称 如(test.sh) 不包含路径
     * @param data 脚本内容
     * @return true写入成功 false写入失败
     */
    boolean writeSh(String file, String... data);

    /**
     * 向脚本写入信息
     *
     * @param file   脚本文件名称 如(test.sh) 不包含路径
     * @param append 是否拼接
     * @param data   脚本内容
     * @return true写入成功 false写入失败
     */
    boolean writeSh(String file, boolean append, String... data);

}