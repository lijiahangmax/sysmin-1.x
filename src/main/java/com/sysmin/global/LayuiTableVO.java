package com.sysmin.global;

/**
 * @author:Li
 * @time: 2019/1/6 18:59
 * @version: 1.0.0
 * layui表格返回对象
 */
public class LayuiTableVO {

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 总数
     */
    private int count;

    /**
     * 数据
     */
    private Object data;

    public LayuiTableVO() {
    }

    public LayuiTableVO(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public LayuiTableVO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public LayuiTableVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCount() {
        return count;
    }

    public LayuiTableVO setCount(int count) {
        this.count = count;
        return this;
    }

    public Object getData() {
        return data;
    }

    public LayuiTableVO setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "LayuiTableVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
