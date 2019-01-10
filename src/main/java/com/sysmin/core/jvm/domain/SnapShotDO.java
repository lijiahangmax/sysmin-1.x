package com.sysmin.core.jvm.domain;

/**
 * @author:Li
 * @time: 2019/1/9 18:36
 * @version: 1.0.0
 */
public class SnapShotDO {

    /**
     * pid快照
     */
    private int pid;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 日期
     */
    private String date;

    /**
     * 文件路径
     */
    private String path;

    public SnapShotDO() {
    }

    public SnapShotDO(int pid, long size, String date, String path) {
        this.pid = pid;
        this.size = size;
        this.date = date;
        this.path = path;
    }

    public int getPid() {
        return pid;
    }

    public SnapShotDO setPid(int pid) {
        this.pid = pid;
        return this;
    }

    public long getSize() {
        return size;
    }

    public SnapShotDO setSize(long size) {
        this.size = size;
        return this;
    }

    public String getPath() {
        return path;
    }

    public SnapShotDO setPath(String path) {
        this.path = path;
        return this;
    }

    public String getDate() {
        return date;
    }

    public SnapShotDO setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "SnapShotDO{" +
                "pid=" + pid +
                ", size=" + size +
                ", date='" + date + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
