package com.sysmin.core.jvm;

import com.sysmin.global.GlobalConfig;

/**
 * @author:Li
 * @time: 2019/1/2 20:50
 * @version: 1.0.0
 */
public class JavaProperties {

    static {
        GlobalConfig.setValue("javahome", System.getenv("JAVA_HOME"));
        GlobalConfig.setValue("javaversion", version());
        GlobalConfig.setValue("javavendor", System.getProperty("java.vendor"));
    }

    /**
     * JAVA_HOME
     */
    public static final String JAVA_HOME = (String) GlobalConfig.getValue("javahome");

    /**
     * java -version
     */
    public static final String JAVA_VERSION = (String) GlobalConfig.getValue("javaversion");

    /**
     * java供应商
     */
    public static final String JAVA_VENDOR = (String) GlobalConfig.getValue("javavendor");

    /**
     * java -version拼接
     *
     * @return
     */
    public static String version() {
        return new StringBuilder().append("java version: ")
                .append("\"" + System.getProperty("java.version") + "\"")
                .append("\n")
                .append(System.getProperty("java.runtime.name") + " (build ")
                .append(System.getProperty("java.version") + "-" + (System.getProperty("java.vm.version")).split("-")[1] + ")")
                .append("\n")
                .append(System.getProperty("java.vm.name") + " (build ")
                .append(System.getProperty("java.vm.version") + "," + System.getProperty("java.vm.info") + ")")
                .toString();
    }

    /**
     * @return java虚拟机现在已经从操作系统那里挖过来的内存大小，也就是java虚拟机这个进程当时所占用的所有 内存。
     * 如果在运行java的时候没有添加-Xms参数，那么，在java程序运行的过程的，
     * 内存总是慢慢的从操作系统那里挖的，基本上是用多少挖多少，直 挖到maxMemory()为止，所以totalMemory()是慢慢增大的。
     * 如果用了-Xms参数，程序在启动的时候就会无条件的从操作系统中挖- Xms后面定义的内存数，然后在这些内存用的差不多的时候，再去挖
     */
    public static long getJVMTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * @return 在java程序运行的过程的，内存总是慢慢的从操 作系统那里挖的，基本上是用多少挖多少
     * 但是java虚拟机100％的情况下是会稍微多挖一点的，这些挖过来而又没有用上的内存
     * 实际上就是 freeMemory()，所以freeMemory()的值一般情况下都是很小的
     * 如果你在运行java程序的时候使用了-Xms，这个时候因为程序在启动的时候就会无条件的从操作系统中挖-Xms后面定义的内存数
     * 这个时候，挖过来的内存可能大部分没用上，所以这个时候freeMemory()可 能会有些大。
     */
    public static long getJVMFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * @return java虚拟机（这个进程）能构从操作系统那里挖到的最大的内存，以字节为单位
     * 如果在运行java程序的时 候，没有添加-Xmx参数，那么就是64兆，也就是说maxMemory()返回的大约是64*1024*1024字节
     * 这是java虚拟机默认情况下能 从操作系统那里挖到的最大的内存。如果添加了-Xmx参数，将以这个参数后面的值为准
     * 例如java -cp ClassPath -Xmx512m ClassName，那么最大内存就是512*1024*1024字节。
     */
    public static long getJVMMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

}
