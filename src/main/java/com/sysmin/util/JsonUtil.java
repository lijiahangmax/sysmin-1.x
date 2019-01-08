package com.sysmin.util;

import com.alibaba.fastjson.JSON;

/**
 * @author:Li
 * @time: 2018/10/15 9:19
 * @version: 1.0
 */
public class JsonUtil {

    /**
     * 数据转json的方法
     *
     * @param data 需要转换的数据
     * @return json串
     */
    public static String objToJson(Object data) {
        return JSON.toJSONString(data);
    }

    /**
     * json转对象的方法
     *
     * @param json  json串
     * @param clazz 实体对象类
     * @param <T>   实体对象
     * @return json转换的对象
     */
    public static <T> T jsonToObj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

}
