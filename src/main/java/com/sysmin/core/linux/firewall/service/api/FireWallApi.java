package com.sysmin.core.linux.firewall.service.api;

import com.sysmin.core.linux.firewall.domain.FireWallDO;

import java.io.IOException;
import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/19 9:33
 * @version: 1.0.0
 */
public interface FireWallApi {

    /**
     * 操作类型 1表示操作成功 0表示操作失败
     * 判断类型 1表示true 0表示false
     */

    /**
     * int结果转boolean
     *
     * @param result
     * @return
     */
    default boolean intToBoolean(int result) {
        return result == 1;
    }

    /**
     * 判断object是否是string/int类型的
     *
     * @param o
     * @return
     */
    default boolean checkValid(Object o) {
        return (o instanceof String || o instanceof Integer);
    }

    /**
     * 是否开启
     *
     * @return
     * @throws IOException
     */
    int isOpen() throws IOException;

    /**
     * 开启防火墙
     *
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * 关闭防火墙
     *
     * @throws IOException
     */
    void stop() throws IOException;

    /**
     * 重启防火墙
     *
     * @throws IOException
     */
    void restart() throws IOException;

    /**
     * 添加端口/服务/端口-端口约束
     *
     * @param o
     * @return
     * @throws IOException
     */
    int addNorm(Object o) throws IOException;

    /**
     * 删除端口/服务/端口-端口约束约束
     *
     * @param o
     * @return
     * @throws IOException
     */
    int removeNorm(Object o) throws IOException;


    /**
     * 获得防火墙状态
     *
     * @return
     * @throws IOException
     */
    List<FireWallDO> getStatus() throws IOException;

    /**
     * 是否开放端口/服务
     *
     * @param o 要查询的端口/服务
     * @return
     * @throws IOException
     */
    int isOpenPort(Object o) throws IOException;

    /**
     * 永久开启防火墙 开机启动
     *
     * @throws IOException
     */
    void alwaysOn() throws IOException;

    /**
     * 永久关闭防火墙 开机不启动
     *
     * @throws IOException
     */
    void alwaysOff() throws IOException;


}
