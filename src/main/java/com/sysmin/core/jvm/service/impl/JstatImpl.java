package com.sysmin.core.jvm.service.impl;

import com.sysmin.core.jvm.domain.jstat.*;
import com.sysmin.core.jvm.enums.JstatType;
import com.sysmin.core.jvm.service.api.JstatApi;
import com.sysmin.global.BaseContinueOut;
import com.sysmin.util.DateUtil;
import com.sysmin.util.JsonUtil;
import com.sysmin.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.StringTokenizer;

/**
 * @author:Li
 * @time: 2018/12/31 19:53
 * @version: 1.0.0
 */
@Service
@Scope("prototype")
public class JstatImpl extends BaseContinueOut implements JstatApi {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 命令类型
     * 0:class  1:compiler  2:gcutil  3:printcompilation  4:gc
     */
    private int type = 0;

    /**
     * 获得类加载信息
     *
     * @param data 数据
     * @param pid  进程id
     */
    private void getClassLoader(String data, int pid) {
        if (!data.contains("Loaded")) {
            System.out.println(pid + " ---- " + data);
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            ClassLoaderDO loader = new ClassLoaderDO()
                    .setLoaded(Integer.valueOf(token.nextToken()))
                    .setLoadedBytes(Double.valueOf(token.nextToken()))
                    .setUnloaded(Integer.valueOf(token.nextToken()))
                    .setUnloadedBytes(Double.valueOf(token.nextToken()))
                    .setTime(Double.valueOf(token.nextToken()))
                    .setDate(DateUtil.getNowDate(DateUtil.HOUR + "时" + DateUtil.MINUTE + "分"));
            simpMessagingTemplate.convertAndSendToUser("test", "/classloader/" + pid, JsonUtil.objToJson(loader));
        }
    }

    /**
     * 获得jvm编译失败信息
     *
     * @param data 数据
     * @param pid  进程id
     */
    private void getCompiler(String data, int pid) {
        if (!data.contains("Compiled")) {
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            CompilerDO compiler = new CompilerDO()
                    .setCompiled(Integer.valueOf(token.nextToken()))
                    .setFailed(Integer.valueOf(token.nextToken()))
                    .setInvalid(Integer.valueOf(token.nextToken()))
                    .setTime(Double.valueOf(token.nextToken()))
                    .setFailedType(Integer.valueOf(token.nextToken()));
            while (token.hasMoreTokens()) {
                compiler.setFailedMethod(compiler.getFailedMethod() + " " + token.nextToken());
            }
            System.out.println(JsonUtil.objToJson(compiler));
        }
    }

    /**
     * 统计gc信息
     *
     * @param data 数据
     * @param pid  进程id
     */
    private void getGcUtil(String data, int pid) {
        if (!data.contains("S0")) {
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            GcUtilDO gcutil = new GcUtilDO()
                    .setS0(Double.valueOf(token.nextToken()))
                    .setS1(Double.valueOf(token.nextToken()))
                    .setE(Double.valueOf(token.nextToken()))
                    .setO(Double.valueOf(token.nextToken()))
                    .setM(Double.valueOf(token.nextToken()))
                    .setCcs(Double.valueOf(token.nextToken()))
                    .setYgc(Integer.valueOf(token.nextToken()))
                    .setYgct(Double.valueOf(token.nextToken()))
                    .setFgc(Integer.valueOf(token.nextToken()))
                    .setFgct(Double.valueOf(token.nextToken()))
                    .setGct(Double.valueOf(token.nextToken()));
            simpMessagingTemplate.convertAndSendToUser("test", "", JsonUtil.objToJson(gcutil));
        }
    }

    /**
     * 获得jvm编译信息
     *
     * @param data 数据
     * @param pid  进程id
     */
    private void getCompilation(String data, int pid) {
        if (!data.contains("Compiled")) {
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            CompilationDO compilation = new CompilationDO()
                    .setCompiled(Integer.valueOf(token.nextToken()))
                    .setSize(Integer.valueOf(token.nextToken()))
                    .setType(Integer.valueOf(token.nextToken()));
            while (token.hasMoreTokens()) {
                compilation.setMethod(compilation.getMethod() + " " + token.nextToken());
            }
            simpMessagingTemplate.convertAndSendToUser("test", "", JsonUtil.objToJson(compilation));
        }
    }

    /**
     * 获得堆gc信息
     *
     * @param data 数据
     * @param pid  进程id
     */
    private void getGcHeap(String data, int pid) {
        if (!data.contains("S0C")) {
            StringTokenizer token = new StringTokenizer(StringUtil.replaceBlank(data, " "), " ");
            GcHeapDO heap = new GcHeapDO()
                    .setS0c(Double.valueOf(token.nextToken()))
                    .setS1c(Double.valueOf(token.nextToken()))
                    .setS0u(Double.valueOf(token.nextToken()))
                    .setS1u(Double.valueOf(token.nextToken()))
                    .setEc(Double.valueOf(token.nextToken()))
                    .setEu(Double.valueOf(token.nextToken()))
                    .setOc(Double.valueOf(token.nextToken()))
                    .setOu(Double.valueOf(token.nextToken()))
                    .setMc(Double.valueOf(token.nextToken()))
                    .setMu(Double.valueOf(token.nextToken()))
                    .setCcsc(Double.valueOf(token.nextToken()))
                    .setCcsu(Double.valueOf(token.nextToken()))
                    .setYgc(Integer.valueOf(token.nextToken()))
                    .setYgct(Double.valueOf(token.nextToken()))
                    .setFgc(Integer.valueOf(token.nextToken()))
                    .setFgct(Double.valueOf(token.nextToken()))
                    .setGct(Double.valueOf(token.nextToken()));
            simpMessagingTemplate.convertAndSendToUser("test", "", JsonUtil.objToJson(heap));
        }
    }


    @Override
    public void jstat(int id, JstatType type) {
        String command = type.getType(id);
        // System.out.println("jstat id: " + id);
        // System.out.println("jstat command: " + command);
        this.type = type.getType();
        super.subscribe(command, true);
    }

    @Override
    public void jstat(int id, int time, JstatType type) {
        String command = type.getType(id, time);
        this.type = type.getType();
        super.subscribe(command, true);
    }

    @Override
    protected void invoke(String data, int pid) {
        if (type == 0) {
            getClassLoader(data, pid);
        } else if (type == 1) {
            getCompiler(data, pid);
        } else if (type == 2) {
            getGcUtil(data, pid);
        } else if (type == 3) {
            getCompilation(data, pid);
        } else if (type == 4) {
            getGcHeap(data, pid);
        }
    }

    @Override
    protected void error(String data) {
        System.out.println("error: " + data);
    }

    @Override
    protected void destroy() {
        System.out.println("destroy");
    }
}
