package com.sysmin.core.system.domain;

/**
 * @author:Li
 * @time: 2018/12/26 11:20
 * @version: 1.0.0
 * df -l
 * 不能持续输出
 */
public class DiskDO {

    /**
     * 文件系统
     */
    private String fileSystem;

    /**
     * 1k块 总量
     */
    private Long blocks;

    /**
     * 已使用
     */
    private Long used;

    /**
     * 未使用
     */
    private Long available;

    /**
     * 使用百分比
     */
    private String use;

    /**
     * 挂载点
     */
    private String mounted;

    public DiskDO() {
    }

    public DiskDO(String fileSystem, Long blocks, Long used, Long available, String use, String mounted) {
        this.fileSystem = fileSystem;
        this.blocks = blocks;
        this.used = used;
        this.available = available;
        this.use = use;
        this.mounted = mounted;
    }

    public String getFileSystem() {
        return fileSystem;
    }

    public DiskDO setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    public Long getBlocks() {
        return blocks;
    }

    public DiskDO setBlocks(Long blocks) {
        this.blocks = blocks;
        return this;
    }

    public Long getUsed() {
        return used;
    }

    public DiskDO setUsed(Long used) {
        this.used = used;
        return this;
    }

    public Long getAvailable() {
        return available;
    }

    public DiskDO setAvailable(Long available) {
        this.available = available;
        return this;
    }

    public String getUse() {
        return use;
    }

    public DiskDO setUse(String use) {
        this.use = use;
        return this;
    }

    public String getMounted() {
        return mounted;
    }

    public DiskDO setMounted(String mounted) {
        this.mounted = mounted;
        return this;
    }

    @Override
    public String toString() {
        return "DiskDO{" +
                "fileSystem='" + fileSystem + '\'' +
                ", blocks=" + blocks +
                ", used=" + used +
                ", available=" + available +
                ", use='" + use + '\'' +
                ", mounted='" + mounted + '\'' +
                '}';
    }

    public void clear() {
        this.setFileSystem(null)
                .setBlocks(null)
                .setAvailable(null)
                .setUse(null).
                setUsed(null).
                setMounted(null);
    }

}
