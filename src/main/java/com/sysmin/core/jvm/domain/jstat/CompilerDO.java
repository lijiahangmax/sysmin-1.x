package com.sysmin.core.jvm.domain.jstat;

/**
 * @author:Li
 * @time: 2018/12/30 18:44
 * @version: 1.0.0
 * jstat -compiler pid
 */
public class CompilerDO {

    /**
     * 编译任务执行数量
     */
    private Integer compiled;

    /**
     * 编译任务执行失败数量
     */
    private Integer failed;

    /**
     * 编译任务执行失效数量
     */
    private Integer invalid;

    /**
     * 编译任务消耗时间
     */
    private Double time;

    /**
     * 最后一个编译失败任务的类型
     */
    private Integer failedType;

    /**
     * 最后一个编译失败任务所在的类及方法
     */
    private String failedMethod;

    public Integer getCompiled() {
        return compiled;
    }

    public CompilerDO setCompiled(Integer compiled) {
        this.compiled = compiled;
        return this;
    }

    public Integer getFailed() {
        return failed;
    }

    public CompilerDO setFailed(Integer failed) {
        this.failed = failed;
        return this;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public CompilerDO setInvalid(Integer invalid) {
        this.invalid = invalid;
        return this;
    }

    public Double getTime() {
        return time;
    }

    public CompilerDO setTime(Double time) {
        this.time = time;
        return this;
    }

    public Integer getFailedType() {
        return failedType;
    }

    public CompilerDO setFailedType(Integer failedType) {
        this.failedType = failedType;
        return this;
    }

    public String getFailedMethod() {
        return failedMethod;
    }

    public CompilerDO setFailedMethod(String failedMethod) {
        this.failedMethod = failedMethod;
        return this;
    }

    @Override
    public String toString() {
        return "CompilerDO{" +
                "compiled=" + compiled +
                ", failed=" + failed +
                ", invalid=" + invalid +
                ", time=" + time +
                ", failedType=" + failedType +
                ", failedMethod='" + failedMethod + '\'' +
                '}';
    }

    public CompilerDO clear() {
        compiled = null;
        failed = null;
        invalid = null;
        time = null;
        failedType = null;
        failedMethod = null;
        return this;
    }

}
