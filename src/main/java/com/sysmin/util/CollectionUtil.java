package com.sysmin.util;

import java.util.List;

/**
 * @author:Li
 * @time: 2019/2/19 12:03
 * @version: 1.0.0
 */
public class CollectionUtil {

    /**
     * 保留集合的后几位
     *
     * @param list 集合
     * @param len  保留几位
     * @return 新集合
     */
    public static List cutList(List list, int len) {
        if (len < list.size()) {
            int size = list.size();
            for (int i = 0; i < size - len; i++) {
                list.remove(0);
            }
        }
        return list;
    }

}
