package com.sysmin.core.jvm.enums;

/**
 * @author:Li
 * @time: 2018/12/31 19:43
 * @version: 1.0.0
 */
public enum JpsType {

    /**
     * 默认显示 jps
     */
    DEFAULT,

    /**
     * 长类型
     */
    LONG;

    /**
     * 命令类型 0:jps  1:jps -l
     */
    private int type;

    public int getCType() {
        return type;
    }

    /**
     * 封装命令
     *
     * @return
     */
    public String getType() {
        switch (this) {
            case DEFAULT:
                type = 0;
                return "jps";
            case LONG:
                type = 1;
                return "jps -l";
            default:
                type = 0;
                return "jps";
        }
    }

}
