package com.sysmin.core.jvm.enums;

import com.sysmin.global.GlobalConfig;
import com.sysmin.util.FileUtil;

/**
 * @author:Li
 * @time: 2018/12/30 20:23
 * @version: 1.0.0
 */
public enum JmapType {

    /**
     * 堆快照
     */
    DUMP,

    /**
     * 堆对象信息
     */
    HISTO,

    /**
     * 堆信息
     */
    HEAP,

    /**
     * 堆中未完成对象信息
     */
    FINALIZERINFO;

    /**
     * 判断快照名称是否正确
     *
     * @param file
     * @return
     */
    private String checkFile(String file) {
        if (file.endsWith(".hprof")) {
            return file;
        } else {
            return file + ".hprof";
        }
    }

    /**
     * 文件路径
     */
    private String path = null;

    /**
     * 将命令封装
     *
     * @param pid 进程id
     * @return 0:命令  1:路径  2:类型(0:dump  1:histo  2:heap  3:finalizerinfo)
     */
    public String[] getTypeToFile(Integer pid) {
        if (((String) GlobalConfig.getValue("OSSystem")).contains("Linux")) {
            switch (this) {
                case DUMP:
                    path = checkFile(FileUtil.checkPath(pid, "heap", "dump_", ".hprof"));
                    return new String[]{"jmap -dump:format=b,file=" + path + " " + pid, path, "0"};
                case HISTO:
                    path = FileUtil.checkPath(pid, "heap", "histo_", ".txt");
                    return new String[]{"jmap -histo" + " " + pid + " > " + path, path, "1"};
                case HEAP:
                    path = FileUtil.checkPath(pid, "heap", "heap_", ".txt");
                    return new String[]{"jmap -heap" + " " + pid + " > " + path, path, "2"};
                case FINALIZERINFO:
                    path = FileUtil.checkPath(pid, "heap", "finalizerinfo_", ".txt");
                    return new String[]{"jmap -finalizerinfo" + " " + pid + " > " + path, path, "3"};
                default:
                    path = checkFile(FileUtil.checkPath(pid, "heap", "dump_", ".hprof"));
                    return new String[]{"jmap -dump:format=b,file=" + path + " " + pid, path, "0"};
            }
        } else {
            switch (this) {
                case DUMP:
                    path = checkFile(FileUtil.checkPath(pid, "heap", "dump_", ".hprof"));
                    return new String[]{"jmap -dump:format=b,file=" + path + " " + pid, path, "0"};
                case HISTO:
                    path = FileUtil.checkPath(pid, "heap", "histo_", ".txt");
                    return new String[]{"jmap -histo" + " " + pid, path, "1"};
                case HEAP:
                    path = FileUtil.checkPath(pid, "heap", "heap_", ".txt");
                    return new String[]{"jmap -heap" + " " + pid, path, "2"};
                case FINALIZERINFO:
                    path = FileUtil.checkPath(pid, "heap", "finalizerinfo_", ".txt");
                    return new String[]{"jmap -finalizerinfo" + " " + pid, path, "3"};
                default:
                    path = checkFile(FileUtil.checkPath(pid, "heap", "dump_", ".hprof"));
                    return new String[]{"jmap -dump:format=b,file=" + path + " " + pid, path, "0"};
            }
        }
    }
}
