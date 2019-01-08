package com.sysmin.core.jvm.domain.jstat;

/**
 * @author:Li
 * @time: 2018/12/30 18:43
 * @version: 1.0.0
 * jstat -class pid
 */
public class ClassLoaderDO {

    /**
     * 装载class的数量
     */
    private Integer loaded;

    /**
     * 装载class所占用空间大小
     */
    private Double loadedBytes;

    /**
     * 卸载class的数量
     */
    private Integer unloaded;

    /**
     * 卸载class所占用空间大小
     */
    private Double unloadedBytes;

    /**
     * 当前时间
     */
    private String date;

    /**
     * 装载和卸载类所花费的时间
     */
    private Double time;

    public Integer getLoaded() {
        return loaded;
    }

    public ClassLoaderDO setLoaded(Integer loaded) {
        this.loaded = loaded;
        return this;
    }

    public Double getLoadedBytes() {
        return loadedBytes;
    }

    public ClassLoaderDO setLoadedBytes(Double loadedBytes) {
        this.loadedBytes = loadedBytes;
        return this;
    }

    public Integer getUnloaded() {
        return unloaded;
    }

    public ClassLoaderDO setUnloaded(Integer unloaded) {
        this.unloaded = unloaded;
        return this;
    }

    public Double getUnloadedBytes() {
        return unloadedBytes;
    }

    public ClassLoaderDO setUnloadedBytes(Double unloadedBytes) {
        this.unloadedBytes = unloadedBytes;
        return this;
    }

    public Double getTime() {
        return time;
    }

    public ClassLoaderDO setTime(Double time) {
        this.time = time;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ClassLoaderDO setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "ClassLoaderDO{" +
                "loaded=" + loaded +
                ", loadedBytes=" + loadedBytes +
                ", unloaded=" + unloaded +
                ", unloadedBytes=" + unloadedBytes +
                ", date='" + date + '\'' +
                ", time=" + time +
                '}';
    }

    public ClassLoaderDO clear() {
        loaded = null;
        loadedBytes = null;
        unloaded = null;
        unloadedBytes = null;
        time = null;
        date = null;
        return this;
    }

}
