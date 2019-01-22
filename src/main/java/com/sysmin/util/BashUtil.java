package com.sysmin.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:Li
 * @time: 2018/12/6 11:06
 * @version: 1.0.0
 * runtime执行命令操作
 */
public class BashUtil {

    /**
     * 将linux命令拼接前缀
     *
     * @param command
     * @return
     */
    public static String[] toLinuxCommand(String command) {
        return new String[]{"/bin/sh", "-c", command};
    }

    /**
     * runtime
     */
    private static Runtime runtime = Runtime.getRuntime();

    /**
     * 执行命令操作  默认不等待执行完毕 UTF-8编码
     *
     * @param commands 命令
     * @return 命令返回信息
     */
    public static String exec(Object commands) {
        try {
            return execute(commands, false, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 执行命令操作 UTF-8编码
     *
     * @param wait     是否等待命令执行
     * @param commands 命令
     * @return 命令返回信息
     */
    public static String exec(Object commands, boolean wait) {
        try {
            return execute(commands, wait, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 执行命令操作  默认不等待执行完毕
     *
     * @param commands 命令
     * @param charset  编码类型
     * @return 命令返回信息
     */
    public static String exec(Object commands, String charset) {
        try {
            return execute(commands, false, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 执行命令操作
     *
     * @param wait     是否等待命令执行
     * @param commands 命令
     * @param charset  编码
     * @return 命令返回信息
     */
    public static String exec(Object commands, boolean wait, String charset) {
        try {
            return execute(commands, wait, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 检查命令是否有效
     *
     * @param commands 命令
     * @return
     * @throws IOException
     */
    public static Process checkCommand(Object commands) throws IOException {
        if (commands instanceof String) {
            return runtime.exec((String) commands);
        } else if (commands instanceof String[]) {
            return runtime.exec((String[]) commands);
        } else {
            throw new RuntimeException("command type exception");
        }
    }

    /**
     * 执行命令的实现方法
     *
     * @param command 命令
     * @param wait    是否等待命令执行完毕
     * @param charset 编码
     * @return
     */
    private static String execute(Object command, boolean wait, String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        Process process = checkCommand(command);
        BufferedReader br;
        if (wait) {
            try {
                int rs = process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        br = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        br = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        process.destroy();
        return sb.toString();
    }

}
