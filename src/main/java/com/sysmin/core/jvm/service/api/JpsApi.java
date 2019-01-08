package com.sysmin.core.jvm.service.api;

import com.sysmin.core.jvm.domain.JpsDO;
import com.sysmin.core.jvm.enums.JpsType;

import java.util.List;

/**
 * @author:Li
 * @time: 2018/12/26 21:28
 * @version: 1.0.0
 */
public interface JpsApi {

    /**
     * jps查看java进程
     *
     * @param type 枚举参数
     * @return jps信息
     */
    List<JpsDO> jps(JpsType type);

}
