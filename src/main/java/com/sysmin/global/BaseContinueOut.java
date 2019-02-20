package com.sysmin.global;

import com.sysmin.util.BashUtil;
import com.sysmin.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author:Li
 * @time: 2018/12/27 11:45
 * @version: 1.0.0
 * 持续获得linux数据的方法
 */
public abstract class BaseContinueOut {

    /**
     * 当前进程
     */
    private Process process;

    /**
     * 查询的jvm进程
     */
    public static List<Process> jvmProcess = new ArrayList<>();

    /**
     * 系统进程进程
     */
    public static Map<String, Process> systemProcess = new HashMap<>();

    /**
     * 提取id / 其他
     *
     * @param command 命令
     * @return id
     */
    private int extractId(String command) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(command.split(" ")));
        if (list.contains("jstat")) {
            return Integer.valueOf(list.get(2));
        }
        return 0;
    }

    /**
     * 将进程添加到集合
     *
     * @param process  进程
     * @param commands 命令
     */
    private void addProcess(Object commands, Process process) {
        String command = "";
        if (commands instanceof String) {
            command = (String) commands;
        } else if (commands instanceof String[]) {
            command = Arrays.toString((String[]) commands);
        }
        if (command.contains("iostat")) {
            systemProcess.put("iostat", process);
        } else if (command.contains("ifstat")) {
            systemProcess.put("ifstat", process);
        } else if (command.contains("free")) {
            systemProcess.put("free", process);
        }
    }

    /**
     * 执行命令 持续获得结果 程序完毕执行重写的销毁方法
     *
     * @param commands 命令
     */
    public void subscribe(Object commands) {
        subscribe(commands, true, false);
    }

    /**
     * 执行命令 持续获得结果 程序完毕执行重写的销毁方法
     *
     * @param commands 命令
     * @param useId    是否传参传入id
     */
    public void subscribe(Object commands, boolean useId) {
        subscribe(commands, true, useId);
    }

    /**
     * 执行命令 持续获得结果
     *
     * @param commands 命令
     * @param destroy  是否执行重写的销毁方法
     * @param useId    是否传参传入id
     */
    public void subscribe(Object commands, boolean destroy, boolean useId) {
        try {
            process = BashUtil.checkCommand(commands);
            addProcess(commands, process);
            int pid = 0;
            String type = "";
            if (useId) {
                if (commands instanceof String) {
                    pid = extractId((String) commands);
                    type = (String) commands;
                } else if (commands instanceof String[]) {
                    pid = extractId(((String[]) commands)[2]);
                    type = ((String[]) commands)[2];
                }
                jvmProcess.add(process);
            }
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    invoke(line, pid, type);
                }
            }
            reader.close();
            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    error(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            if (e.getMessage().contains("Stream Closed")) {
                // 流已关闭
            } else if (e.getMessage().contains("Cannot run program")) {
                // 未安装应用
                error(e.getMessage());
            } else if (e.getMessage().contains("管道的另一端上无任何进程")) {
                // 已关闭
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                process.destroy();
                if (destroy) {
                    destroy();
                }
            } catch (Exception e) {
                // 防止process的NPE
            }
        }
    }

    /**
     * 获得结果执行
     *
     * @param data    数据
     * @param pid     进程id
     * @param command 命令
     */
    protected abstract void invoke(String data, int pid, String command);

    /**
     * 处理失败执行
     *
     * @param data 数据
     */
    protected abstract void error(String data);

    /**
     * 执行完毕调用
     */
    protected abstract void destroy();

    /**
     * 取消订阅 (杀死进程)
     */
    protected void unsubscribe() {
        if (process != null) {
            process.destroy();
        }
    }

}
