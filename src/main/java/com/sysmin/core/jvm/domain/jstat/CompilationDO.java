package com.sysmin.core.jvm.domain.jstat;

/**
 * @author:Li
 * @time: 2018/12/30 19:41
 * @version: 1.0.0
 * jstat -printcompilation pid
 */
public class CompilationDO {

    /**
     * 编译任务的数量
     */
    private Integer compiled;

    /**
     * 方法生成的字节码的大小
     */
    private Integer size;

    /**
     * 编译类型
     */
    private Integer type;

    /**
     * 类名和方法名用来标识编译的方法。类名使用/做为一个命名空间分隔符。方法名是给定类中的方法
     */
    private String method;

    public Integer getCompiled() {
        return compiled;
    }

    public CompilationDO setCompiled(Integer compiled) {
        this.compiled = compiled;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public CompilationDO setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public CompilationDO setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public CompilationDO setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public String toString() {
        return "CompilationDO{" +
                "compiled=" + compiled +
                ", size=" + size +
                ", type=" + type +
                ", method='" + method + '\'' +
                '}';
    }

    public CompilationDO clear() {
        compiled = null;
        size = null;
        type = null;
        method = null;
        return this;
    }

}
